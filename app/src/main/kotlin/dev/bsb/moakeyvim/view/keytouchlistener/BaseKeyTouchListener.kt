package dev.bsb.moakeyvim.view.keytouchlistener

import android.content.Context
import android.content.Intent
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import dev.bsb.moakeyvim.OpenMoaIME
import dev.bsb.moakeyvim.config.Config
import dev.bsb.moakeyvim.config.HapticStrength
import dev.bsb.moakeyvim.config.SoundVolume
import dev.bsb.moakeyvim.config.SoundType
import dev.bsb.moakeyvim.settings.SettingsPreferences
import dev.bsb.moakeyvim.view.feedback.KeyFeedbackPlayer
import dev.bsb.moakeyvim.view.message.BaseKeyMessage
import dev.bsb.moakeyvim.view.message.SpecialKeyMessage
import dev.bsb.moakeyvim.view.message.StringKeyMessage
import dev.bsb.moakeyvim.view.skin.SkinApplier

open class BaseKeyTouchListener(context: Context) : OnTouchListener, KoinComponent {

    protected val config: Config by inject()
    private val feedbackPlayer: KeyFeedbackPlayer by inject()

    private val broadcastManager = LocalBroadcastManager.getInstance(context)
    private val skin = SettingsPreferences.getKeyboardSkin(context)
    private val backgrounds = listOf(
        SkinApplier.buildKeyDrawable(context, skin, pressed = true),
        SkinApplier.buildKeyDrawable(context, skin, pressed = false),
    )

    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> {
                view.background = backgrounds[0]
                playFeedback()
            }
            MotionEvent.ACTION_CANCEL -> {
                view.background = backgrounds[1]
            }
            MotionEvent.ACTION_UP -> {
                view.background = backgrounds[1]
                view.performClick()
            }
        }
        return true
    }

    protected fun playFeedback() {
        val strength = config.hapticStrength
        if (strength != HapticStrength.OFF) {
            feedbackPlayer.playHaptic(strength.durationMs, strength.amplitude)
        }
        val volume = config.soundVolume
        if (volume != SoundVolume.OFF) {
            feedbackPlayer.playSound(config.soundType.effectId, volume.volume)
        }
    }

    protected fun sendKeyMessage(keyMessage: BaseKeyMessage) {
        broadcastManager.sendBroadcast(
            Intent(OpenMoaIME.INTENT_ACTION).apply {
                putExtra(OpenMoaIME.EXTRA_NAME, when (keyMessage) {
                    is StringKeyMessage -> keyMessage.key
                    is SpecialKeyMessage -> keyMessage.key
                    else -> ""
                })
            }
        )
    }

}
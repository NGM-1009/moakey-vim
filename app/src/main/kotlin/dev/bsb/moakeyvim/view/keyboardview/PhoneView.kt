package dev.bsb.moakeyvim.view.keyboardview

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import org.koin.core.component.KoinComponent
import dev.bsb.moakeyvim.R
import dev.bsb.moakeyvim.databinding.PhoneViewBinding
import dev.bsb.moakeyvim.view.keytouchlistener.EnterKeyTouchListener
import dev.bsb.moakeyvim.view.keytouchlistener.FunctionalKeyTouchListener
import dev.bsb.moakeyvim.view.keytouchlistener.QwertyKeyTouchListener
import dev.bsb.moakeyvim.view.keytouchlistener.RepeatKeyTouchListener
import dev.bsb.moakeyvim.view.keytouchlistener.SimpleKeyTouchListener
import dev.bsb.moakeyvim.view.keytouchlistener.SpaceKeyTouchListener
import dev.bsb.moakeyvim.view.message.SpecialKey
import dev.bsb.moakeyvim.view.message.SpecialKeyMessage
import dev.bsb.moakeyvim.view.message.StringKeyMessage
import dev.bsb.moakeyvim.view.preview.KeyPreviewController
import dev.bsb.moakeyvim.view.preview.QuickPhraseMenuPopup
import dev.bsb.moakeyvim.view.skin.SkinApplier
import dev.bsb.moakeyvim.quickphrase.NumberLongKey
import dev.bsb.moakeyvim.settings.SettingsPreferences

class PhoneView : ConstraintLayout, KoinComponent {

    constructor(context: Context) : super(context) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init()
    }

    var onEditNumberLongKeyRequest: ((NumberLongKey) -> Unit)? = null

    private lateinit var binding: PhoneViewBinding
    private var page = 0
    private var enterKeyListener: EnterKeyTouchListener? = null
    private var previewController: KeyPreviewController? = null
    private val numberKeyListeners = mutableListOf<QwertyKeyTouchListener>()
    private val numberKeyPopups = List(10) { QuickPhraseMenuPopup(context) }
    private val prefs by lazy {
        context.getSharedPreferences(SettingsPreferences.PREFS_NAME, Context.MODE_PRIVATE)
    }
    private val numberPrefKeys = NumberLongKey.values().map { it.prefKey }.toSet()
    private val prefChangeListener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
        if (key in numberPrefKeys && ::binding.isInitialized && page == 0) {
            updateNumberKeyHints()
        }
    }

    private fun init() {
        inflate(context, R.layout.phone_view, this)
        binding = PhoneViewBinding.bind(this)
        previewController = KeyPreviewController({ false }, SettingsPreferences.getKeyboardSkin(context))
        setOnStaticKeyListeners()
        setPageOrNextPage(0, true)
        SkinApplier.apply(this, SettingsPreferences.getKeyboardSkin(context))
    }

    fun setPageOrNextPage(newPage: Int? = null, isInitialize: Boolean = false) {
        if (page == newPage && !isInitialize) {
            return
        }
        page = newPage ?: ((page + 1) % KEY_LIST.size)
        listOf(
            binding.oneKey, binding.twoKey, binding.threeKey, binding.fourKey, binding.fiveKey,
            binding.sixKey, binding.sevenKey, binding.eightKey, binding.nineKey, binding.zeroKey,
        ).mapIndexed { index, view ->
            view.text = KEY_LIST[page][index][0]
            view.tag = KEY_LIST[page][index][1]
        }
        binding.punctuationKey.text = resources.getString(
            if (page == 0) R.string.key_punctuation else R.string.key_one_two_three
        )
        updateNumberKeyListeners()
        if (page == 0) updateNumberKeyHints()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setOnStaticKeyListeners() {
        binding.apply {
            backspaceKey.setOnTouchListener(
                RepeatKeyTouchListener(context, SpecialKeyMessage(SpecialKey.BACKSPACE))
            )
            minusKey.setOnTouchListener(SimpleKeyTouchListener(context, StringKeyMessage("-")))
            dotKey.setOnTouchListener(SimpleKeyTouchListener(context, StringKeyMessage(".")))
            punctuationKey.setOnTouchListener(
                FunctionalKeyTouchListener(context) {
                    setPageOrNextPage()
                    null
                }
            )
            spaceKey.setOnTouchListener(SpaceKeyTouchListener(context))
            enterKeyListener = EnterKeyTouchListener(context)
            enterKey.setOnTouchListener(enterKeyListener)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun updateNumberKeyListeners() {
        numberKeyListeners.forEach { it.cancel() }
        numberKeyListeners.clear()

        val numberKeys = listOf(
            binding.oneKey, binding.twoKey, binding.threeKey, binding.fourKey, binding.fiveKey,
            binding.sixKey, binding.sevenKey, binding.eightKey, binding.nineKey, binding.zeroKey,
        )

        if (page == 0) {
            val longKeys = listOf(
                NumberLongKey.NUM_1, NumberLongKey.NUM_2, NumberLongKey.NUM_3,
                NumberLongKey.NUM_4, NumberLongKey.NUM_5, NumberLongKey.NUM_6,
                NumberLongKey.NUM_7, NumberLongKey.NUM_8, NumberLongKey.NUM_9,
                NumberLongKey.NUM_0,
            )
            numberKeys.zip(longKeys).forEachIndexed { i, (view, longKey) ->
                val listener = QwertyKeyTouchListener(
                    context,
                    previewController,
                    longKeyProvider = { longKey.getPhrase(context) },
                    onTap = { StringKeyMessage(longKey.digit) },
                    quickPhraseMenuPopup = numberKeyPopups[i],
                    onEdit = { onEditNumberLongKeyRequest?.invoke(longKey) },
                )
                numberKeyListeners.add(listener)
                view.setOnTouchListener(listener)
            }
        } else {
            numberKeys.forEach { view ->
                view.keyHint = ""
                view.setOnTouchListener(FunctionalKeyTouchListener(context) {
                    StringKeyMessage(view.tag as String)
                })
            }
        }
    }

    private fun updateNumberKeyHints() {
        if (!::binding.isInitialized || page != 0) return
        listOf(
            binding.oneKey to NumberLongKey.NUM_1, binding.twoKey to NumberLongKey.NUM_2,
            binding.threeKey to NumberLongKey.NUM_3, binding.fourKey to NumberLongKey.NUM_4,
            binding.fiveKey to NumberLongKey.NUM_5, binding.sixKey to NumberLongKey.NUM_6,
            binding.sevenKey to NumberLongKey.NUM_7, binding.eightKey to NumberLongKey.NUM_8,
            binding.nineKey to NumberLongKey.NUM_9, binding.zeroKey to NumberLongKey.NUM_0,
        ).forEach { (view, longKey) ->
            view.keyHint = longKey.getPhrase(context).take(1)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        prefs.registerOnSharedPreferenceChangeListener(prefChangeListener)
        updateNumberKeyHints()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        prefs.unregisterOnSharedPreferenceChangeListener(prefChangeListener)
        previewController?.cancel()
        numberKeyListeners.forEach { it.cancel() }
        numberKeyPopups.forEach { it.dismiss() }
        enterKeyListener?.cancel()
    }

    companion object {
        private val KEY_LIST = listOf(
            listOf(
                listOf("1", "1"), listOf("2", "2"), listOf("3", "3"),
                listOf("4", "4"), listOf("5", "5"), listOf("6", "6"),
                listOf("7", "7"), listOf("8", "8"), listOf("9", "9"),
                listOf("0", "0"),
            ),
            listOf(
                listOf("(", "("), listOf("/", "/"), listOf(")", ")"),
                listOf("N", "N"), listOf("Pause", ","), listOf(",", ","),
                listOf("*", "*"), listOf("Wait", ";"), listOf("#", "#"),
                listOf("+", "+"),
            ),
        )
    }

}

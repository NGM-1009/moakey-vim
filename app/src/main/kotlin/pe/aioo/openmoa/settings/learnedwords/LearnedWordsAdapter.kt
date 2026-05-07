package pe.aioo.openmoa.settings.learnedwords

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pe.aioo.openmoa.R
import pe.aioo.openmoa.databinding.ItemLearnedBlacklistBinding
import pe.aioo.openmoa.databinding.ItemLearnedWordBinding

class LearnedWordsAdapter(
    private val onWordClick: (LearnedWordItem.Word) -> Unit,
    private val onWordDelete: (LearnedWordItem.Word) -> Unit,
    private val onBlacklistRelease: (LearnedWordItem.Blacklist) -> Unit,
) : ListAdapter<LearnedWordItem, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private const val VIEW_TYPE_WORD = 0
        private const val VIEW_TYPE_BLACKLIST = 1

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<LearnedWordItem>() {
            override fun areItemsTheSame(old: LearnedWordItem, new: LearnedWordItem): Boolean =
                old.word == new.word && old::class == new::class

            override fun areContentsTheSame(old: LearnedWordItem, new: LearnedWordItem): Boolean =
                old == new
        }
    }

    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is LearnedWordItem.Word -> VIEW_TYPE_WORD
            is LearnedWordItem.Blacklist -> VIEW_TYPE_BLACKLIST
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_WORD -> WordViewHolder(
                ItemLearnedWordBinding.inflate(inflater, parent, false)
            )
            VIEW_TYPE_BLACKLIST -> BlacklistViewHolder(
                ItemLearnedBlacklistBinding.inflate(inflater, parent, false)
            )
            else -> throw IllegalStateException("Unknown viewType: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is LearnedWordItem.Word -> (holder as WordViewHolder).bind(item)
            is LearnedWordItem.Blacklist -> (holder as BlacklistViewHolder).bind(item)
        }
    }

    inner class WordViewHolder(
        private val binding: ItemLearnedWordBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: LearnedWordItem.Word) {
            binding.wordText.text = item.word
            binding.countText.text = item.count.toString()
            binding.root.setOnClickListener { onWordClick(item) }
            binding.deleteButton.setOnClickListener { onWordDelete(item) }
        }
    }

    inner class BlacklistViewHolder(
        private val binding: ItemLearnedBlacklistBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: LearnedWordItem.Blacklist) {
            val ctx = binding.root.context
            binding.wordText.text = item.word
            binding.langLabel.text = ctx.getString(
                if (item.isEn) R.string.settings_lang_label_en
                else R.string.settings_lang_label_ko
            )
            binding.root.setOnClickListener { onBlacklistRelease(item) }
            binding.releaseButton.setOnClickListener { onBlacklistRelease(item) }
        }
    }
}

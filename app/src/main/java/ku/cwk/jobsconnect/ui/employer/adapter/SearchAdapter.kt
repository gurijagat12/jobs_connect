package ku.cwk.jobsconnect.ui.employer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import ku.cwk.jobsconnect.R
import ku.cwk.jobsconnect.databinding.LayoutCandidateBinding
import ku.cwk.jobsconnect.interfaces.TagDataListener
import ku.cwk.jobsconnect.ui.employer.model.SearchData

class SearchAdapter(
    private val dataList: ArrayList<SearchData>,
    private val listener: TagDataListener
) : RecyclerView.Adapter<SearchAdapter.CustomViewHolder>() {

    inner class CustomViewHolder(private val binding: LayoutCandidateBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val context = binding.root.context

        init {
            binding.cvCandidate.setOnClickListener {
                listener.sendData("", dataList[adapterPosition].personal.email)
            }
        }

        internal fun bind(data: SearchData) {
            binding.apply {
                tvName.text = context.getString(
                    R.string.ph_space,
                    data.personal.firstName,
                    data.personal.lastName
                )
                data.personal.apply {
                    tvEmail.text = email
                    tvMobile.text = phone
                }
                val skillCommas = data.skills
                val list: List<String> = skillCommas.split(",").toList()
                list.forEach { addChip(it) }
            }
        }

        private fun addChip(skill: String) {
            val chip = Chip(context)
            chip.text = skill
            chip.chipStrokeWidth = 4f
            chip.chipBackgroundColor =
                ContextCompat.getColorStateList(context, R.color.purple_500)
            chip.chipStrokeColor = ContextCompat.getColorStateList(context, R.color.teal_700)
            //chip.isCloseIconVisible = true
            chip.setTextColor(context.getColor(R.color.white))
            binding.chipSkill.addView(chip)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): CustomViewHolder {
        val itemView =
            LayoutCandidateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(dataList[position])
    }
}
package ku.cwk.jobsconnect.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ku.cwk.jobsconnect.R
import ku.cwk.jobsconnect.databinding.LayoutEducationBinding
import ku.cwk.jobsconnect.ui.home.model.Experience
import ku.cwk.jobsconnect.ui.home.model.Qualification
import ku.cwk.jobsconnect.util.convertDateFormat

class ExperienceAdapter(
    private val dataList: ArrayList<Experience>
) : RecyclerView.Adapter<ExperienceAdapter.CustomViewHolder>() {

    inner class CustomViewHolder(private val binding: LayoutEducationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val context = binding.root.context

        internal fun bind(data: Experience) {
            binding.apply {
                tvRole.text = data.jobTitle
                tvCompany.text = data.companyName
                tvDesc.text = data.jobRole
                tvYear.text = context.getString(
                    R.string.ph_date_years,
                    convertDateFormat(data.fromDate ?: ""),
                    convertDateFormat(data.toDate ?: "")
                )
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): CustomViewHolder {
        val itemView =
            LayoutEducationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(dataList[position])
    }
}
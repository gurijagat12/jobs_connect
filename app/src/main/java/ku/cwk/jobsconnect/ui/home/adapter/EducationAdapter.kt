package ku.cwk.jobsconnect.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ku.cwk.jobsconnect.R
import ku.cwk.jobsconnect.databinding.LayoutEducationBinding
import ku.cwk.jobsconnect.ui.home.model.Qualification
import ku.cwk.jobsconnect.util.convertDateFormat

class EducationAdapter(
    private val dataList: ArrayList<Qualification>
) : RecyclerView.Adapter<EducationAdapter.CustomViewHolder>() {

    inner class CustomViewHolder(private val binding: LayoutEducationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val context = binding.root.context

        init {
            binding.ivJob.setImageResource(R.drawable.round_menu_book_24)
        }

        internal fun bind(data: Qualification) {
            binding.apply {
                tvRole.text = data.instituteName
                tvCompany.text = data.courseName
                tvDesc.text = context.getString(R.string.ph_grades, data.grades)
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
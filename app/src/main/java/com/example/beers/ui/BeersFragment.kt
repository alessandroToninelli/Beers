package com.example.beers.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.beers.R
import com.example.beers.databinding.BeersFragmentBinding
import com.example.beers.model.BeerParam
import com.leavjenn.smoothdaterangepicker.date.SmoothDateRangePickerFragment
import org.koin.android.viewmodel.ext.android.viewModel

class BeersFragment : Fragment() {

    private lateinit var binding: BeersFragmentBinding

    private val viewModel: BeersViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.beers_fragment, container, false)

        binding.model = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.beerList.adapter = BeerAdapter{
            println(it)
        }

        binding.timeButton.setOnClickListener {

            when (binding.timeButton.text) {
                getString(R.string.beers_fragment_reset_interval_button) -> {
                    viewModel.getBeerByInterval(null)
                    binding.timeButton.text = getString(R.string.beers_fragment_set_interval_button)

                }
                getString(R.string.beers_fragment_set_interval_button) -> {
                    val rangePicker =
                        SmoothDateRangePickerFragment.newInstance { view, yearStart, monthStart, dayStart, yearEnd, monthEnd, dayEnd ->

                            println(monthStart)

                            viewModel.getBeerByInterval(
                                BeerParam(
                                    monthEnd + 1,
                                    yearEnd,
                                    monthStart + 1,
                                    yearStart
                                )
                            )

                            binding.timeButton.text =
                                getString(R.string.beers_fragment_reset_interval_button)
                        }

                    rangePicker.setYearRange(1800, 2050)
                    rangePicker.show(activity!!.fragmentManager, "picker")
                }

            }

        }

        return binding.root

    }

}
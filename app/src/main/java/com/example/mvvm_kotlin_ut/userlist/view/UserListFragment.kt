package com.example.mvvm_kotlin_ut.userlist.view

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvm_kotlin_ut.R
import com.example.mvvm_kotlin_ut.replaceFragment
import com.example.mvvm_kotlin_ut.userlist.ItemClickListener
import com.example.mvvm_kotlin_ut.userlist.model.Data
import com.example.mvvm_kotlin_ut.userlist.viewmodel.UserListViewModel
import com.example.mvvm_kotlin_ut.userlist.viewmodel.UserListViewModelFactory
import kotlinx.android.synthetic.main.fragment_user_list.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UserListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserListFragment : Fragment(), ItemClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var userListView : View? = null
    lateinit var viewModel : UserListViewModel
    private var userListAdapter : UserListAdapter? = null
    private var clickedId : Int? = -1
    var mContainerId:Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        userListView =  inflater.inflate(R.layout.fragment_user_list, container, false)
        mContainerId = container?.id?:-1
        return  userListView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initAdapter()
        observeViewModel()

    }


    private fun initViewModel(){
        var userListViewModelFactory = UserListViewModelFactory()
        viewModel = ViewModelProviders.of(this, userListViewModelFactory).get(UserListViewModel::class.java)
    }

    private fun initAdapter(){
        userListAdapter =  UserListAdapter(arrayListOf(),this@UserListFragment.requireActivity(),this)
        userListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = userListAdapter
        }
    }
    override fun onResume() {
        super.onResume()
        viewModel.fetchUserListInfo(2)
    }

    private fun observeViewModel(){
        viewModel.fetchUsersLiveData().observe(viewLifecycleOwner, Observer {
            it?.let {
                userListAdapter?.refreshAdapter(it)
            }
        })

        viewModel.fetchLoadStatus().observe(viewLifecycleOwner, Observer {
            if(!it){
                println(it)
                loading_progress.visibility  = View.GONE
            }
        })

        viewModel.fetchError().observe(viewLifecycleOwner, Observer {
            it?.let {
                if(!TextUtils.isEmpty(it)){
                    Toast.makeText(context,"$it", Toast.LENGTH_LONG).show()
                }

            }
        })
    }


    override fun setClickedInfo(data: Data) {
        clickedId = data.id
        launchDetailFragment()
    }


    private fun launchDetailFragment(){
        var userListDetailFragment = UserListDetailFragment.newInstance(clickedId.toString(),"")
        activity?.replaceFragment(userListDetailFragment,mContainerId)
    }
}
package com.example.fanfics.ui.user.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.fanfics.MainViewModel
import com.example.fanfics.R
import com.example.fanfics.data.models.User
import com.example.fanfics.utils.SessionManager

const val USER_OBJ = "com.example.fanfics.data.models.User"
/**
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()

    private var user: User? = null

    private lateinit var logout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            user = it.getParcelable(USER_OBJ)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_profile, container, false)

        init(view)

        return view
    }

    private inline fun init(view: View){
        logout = view.findViewById(R.id.button_logout)
        logout.setOnClickListener {
            context?.let { viewModel.logout.postValue("Logout") }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param user User.
         * @return A new instance of fragment ProfileFragment.
         */
        @JvmStatic
        fun newInstance(user: User?) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(USER_OBJ, user)
                }
            }
    }
}
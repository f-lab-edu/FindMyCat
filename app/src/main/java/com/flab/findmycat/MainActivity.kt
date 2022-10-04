package com.flab.findmycat

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.flab.findmycat.ui.interfaces.IProgressBarActivity
import com.flab.findmycat.ui.interfaces.ISearchListenerActivity
import com.flab.findmycat.ui.interfaces.ISearchListenerFragment
import com.flab.findmycat.utils.NetworkUtils
import com.flab.findmycat.utils.hide
import com.flab.findmycat.utils.hideKeyboard
import com.flab.findmycat.utils.show
import com.flab.findmycat.utils.toastL
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), IProgressBarActivity, ISearchListenerActivity {

    lateinit var navController: NavController
    private var searchFragment: ISearchListenerFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)

        initSearchListener()
    }

    private fun initSearchListener() {
        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null && query.isNotEmpty()) {
                    if (NetworkUtils.getNetwork(applicationContext)) {
                        searchFragment?.doSearch(query.trim())
                        hideKeyboard()
                    } else
                        this@MainActivity.toastL(resources.getString(R.string.message_offline))
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }

    override fun show() {
        progress_bar.show()
    }

    override fun hide() {
        progress_bar.hide()
    }

    override fun showSearchView(isShown: Boolean) {
        search_view.visibility = if (isShown) View.VISIBLE else View.GONE
    }

    override fun setSearchText(searchQuery: String) {
        search_view.setQuery(searchQuery, false)
    }

    override fun registerSearchFragment(instance: ISearchListenerFragment) {
        searchFragment = instance
    }
}

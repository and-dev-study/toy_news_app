package com.kevin.newsapp.ui.main.headline.children.top

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.kevin.newsapp.R
import com.kevin.newsapp.data.model.news.NewsResponse
import com.kevin.newsapp.databinding.HeadlineChildFragmentTopBinding
import com.kevin.newsapp.ui.article.ArticleActivity
import com.kevin.newsapp.ui.base.BaseChildFragment
import com.kevin.newsapp.ui.main.headline.HeadlineScrollEventListener
import com.kevin.newsapp.ui.main.headline.adapter.HeadlineRecyclerViewAdapter
import com.kevin.newsapp.ui.main.headline.adapter.HeadlineRecyclerViewItemClickListener
import com.kevin.newsapp.util.extensions.autoScaling
import com.kevin.newsapp.util.extensions.gone
import com.kevin.newsapp.util.extensions.onGlobalLayout
import com.kevin.newsapp.util.extensions.visible
import com.kevin.newsapp.util.other.log
import com.kevin.newsapp.util.state.CommonState

class TopFragment: BaseChildFragment<HeadlineChildFragmentTopBinding, TopViewModel>() {

    override val layoutResID: Int
        get() = R.layout.headline_child_fragment_top

    override val modelClass: Class<TopViewModel>
        get() = TopViewModel::class.java

    private val itemClickListener = object: HeadlineRecyclerViewItemClickListener {
        override fun onClick(view: View, url: String) {
            when(view.id) {
                R.id.headlineSaveButton -> {
                    log(message = "save button clicked")
                }
                R.id.headlineShareButton -> {
                    log(message = "share button clicked")
                }
                else -> {
                    val intent = Intent(mContext, ArticleActivity::class.java).putExtra(ArticleActivity.ARTICLE_URL, url)
                    startActivity(intent)
                }
            }
        }
    }

    private val onScrollListener = object: RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            (parentFragment as? HeadlineScrollEventListener)
                    ?.takeIf { dy != 0 }
                    ?.onScale(dy < 0)
        }
    }

    override fun onCreateView() {
        with(mBinding) {
            topLoadingView.onGlobalLayout { topLoadingView.autoScaling(.9f) }

            with(topRecyclerView) {
                adapter = HeadlineRecyclerViewAdapter(mContext, itemClickListener)
                layoutManager = LinearLayoutManager(mContext)

                addOnScrollListener(onScrollListener)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel.topHeadlines.observe(this, Observer {
            when(it) {
                is CommonState.Init -> { showOrHideLoadingView(false) }
                is CommonState.Loading -> { showOrHideLoadingView(true) }
                is CommonState.Success -> { updateData(it.data) }
                is CommonState.Error -> { /* HANDLING ERROR */ }
            }
        })

        mViewModel.fetchTopHeadlines()
    }

    private fun updateData(data: NewsResponse) {
        with(mBinding.topRecyclerView) {
            (adapter as HeadlineRecyclerViewAdapter).updateData(data)
            scheduleLayoutAnimation()
        }
    }

    private fun showOrHideLoadingView(show: Boolean) = with(mBinding.topLoadingView) { if(show) visible() else gone() }

    companion object {
        fun newInstance(): TopFragment = TopFragment()
    }
}

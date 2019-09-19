package cn.com.core.tool;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sxu.refreshlayout.RefreshLayout;

import cn.com.core.R;

/*自定义的控价下拉刷新布局;
* */
public class MyRefreshLayout extends RefreshLayout {

	private TextView refreshText;
	private TextView loadText;
	private ProgressBar headerProgressBar;
	private ProgressBar footerProgressBar;

	public MyRefreshLayout(Context context) {
		this(context, null);
	}

	public MyRefreshLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		setHeaderViewResId(R.layout.item_my_header_layout);
		setFooterViewResId(R.layout.item_my_footer_layout);
	}

	@Override
	protected void addHeaderViewAndFooterView() {
		super.addHeaderViewAndFooterView();
//		mHeaderView = View.inflate(getContext(), R.layout.item_my_header_layout, null);
//		mFooterView = View.inflate(getContext(), R.layout.item_my_footer_layout, null);
//		addView(mHeaderView, 0);
//		addView(mFooterView, getChildCount());

		refreshText = findViewById(R.id.header_text);
		loadText = findViewById(R.id.footer_text);
		headerProgressBar = findViewById(R.id.header_progress);
		footerProgressBar = findViewById(R.id.footer_progress);
	}

	@Override
	protected void showRefreshingLayout() {
		refreshText.setText("正在刷新...");
	}

	@Override
	protected void showLoadingLayout() {
		loadText.setText("正在加载...");
	}

	@Override
	protected void refreshingComplete() {
		refreshText.setText("刷新成功");
		headerProgressBar.setVisibility(INVISIBLE);
	}

	@Override
	protected void loadingComplete() {
		loadText.setText("加载完成");
		footerProgressBar.setVisibility(INVISIBLE);
	}

	@Override
	protected void resetRefreshLayout() {
		refreshText.setText("下拉刷新");
		headerProgressBar.setVisibility(VISIBLE);
	}

	@Override
	protected void resetLoadMoreLayout() {
		loadText.setText("上拉加载");
		footerProgressBar.setVisibility(VISIBLE);
	}

	@Override
	protected void showRefreshReleaseLayout() {
		refreshText.setText("释放立即刷新");
	}

	@Override
	protected void showLoadReleaseLayout() {
		loadText.setText("释放立即加载");
	}
}

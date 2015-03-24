<!-- BEGIN Sidebar -->
<div id="sidebar" class="nav-collapse">
    <!-- BEGIN Navlist -->
    <ul class="nav nav-list">
        <!-- BEGIN Search Form -->
        <!--<li>
            <form target="#" method="GET" class="search-form">
                <span class="search-pan">
                    <button type="submit">
                        <i class="icon-search"></i>
                    </button>
                    <input type="text" name="search" placeholder="Search ..." autocomplete="off" />
                </span>
            </form>
        </li>-->
        <!-- END Search Form -->
        <li>
            <a href="#" class="dropdown-toggle">
                <i class="icon-desktop"></i>
                <span>学生管理</span>
                <b class="arrow icon-angle-right"></b>
            </a>
            <!-- BEGIN Submenu -->
            <ul class="submenu">
                <li><a href="${ctx}">学生列表</a></li>
                <li><a href="${ctx}/action/student/add">添加学生</a></li>
            </ul>
            <!-- END Submenu -->
        </li>

        <li>
            <a href="#" class="dropdown-toggle">
                <i class="icon-book"></i>
                <span>费用记录</span>
                <b class="arrow icon-angle-right"></b>
            </a>

            <!-- BEGIN Submenu -->
            <ul class="submenu">
           		<li><a href="${ctx}/action/analytics/list/">费用统计</a></li>
                <li><a href="${ctx}/action/payment/">缴费记录</a></li>
                <li><a href="${ctx}/action/fee/">费用管理</a></li>
            </ul>
            <!-- END Submenu -->
        </li>
        <li>
            <a href="${ctx}/action/message/index" class="dropdown-toggle">
                <i class="icon-comments"></i>
                <span>消息通知</span>
            </a>
        </li>
    </ul>
    <!-- END Navlist -->

    <!-- BEGIN Sidebar Collapse Button -->
    <div id="sidebar-collapse" class="visible-desktop">
        <i class="icon-double-angle-left"></i>
    </div>
    <!-- END Sidebar Collapse Button -->
</div>
<!-- END Sidebar -->
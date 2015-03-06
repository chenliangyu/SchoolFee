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
                <i class="icon-edit"></i>
                <span>费用查询</span>
                <b class="arrow icon-angle-right"></b>
            </a>

            <!-- BEGIN Submenu -->
            <ul class="submenu">
                <li><a href="add_fee.html">缴费</a></li>
                <li><a href="query_fee.html">查询</a></li>
            </ul>
            <!-- END Submenu -->
        </li>
        <li>
            <a href="message.html" class="dropdown-toggle">
                <i class="icon-edit"></i>
                <span>消息通知</span>
            </a>
        </li>
        <li>
            <a href="skin.html" class="dropdown-toggle">
                <i class="icon-edit"></i>
                <span>皮肤设置</span>
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
<!-- BEGIN Navbar -->
<div id="navbar" class="navbar">
    <div class="navbar-inner">
        <div class="container-fluid">
            <!-- BEGIN Brand -->
            <a href="#" class="brand">
                <small>
                    <i class="icon-desktop"></i>
                    缴费查询系统
                </small>
            </a>
            <!-- END Brand -->

            <!-- BEGIN Responsive Sidebar Collapse -->
            <a href="#" class="btn-navbar collapsed" data-toggle="collapse" data-target=".nav-collapse">
                <i class="icon-reorder"></i>
            </a>
            <!-- END Responsive Sidebar Collapse -->

            <!-- BEGIN Navbar Buttons -->
            <ul class="nav flaty-nav pull-right">
                <!-- BEGIN Button Messages -->
                <li class="hidden-phone">
                    <a href="${ctx}/action/message/index">
                        <i class="icon-envelope"></i>
                        <span class="badge badge-success message_count"></span>
                    </a>
                </li>
                <!-- END Button Messages -->

                <!-- BEGIN Button User -->
                <li class="user-profile">
                    <a data-toggle="dropdown" href="#" class="user-menu dropdown-toggle">
                        <span class="hidden-phone" id="user_info">
                        	${shiro.principal().nickname}
                        </span>
                        <i class="icon-caret-down"></i>
                    </a>

                    <!-- BEGIN User Dropdown -->
                    <ul class="dropdown-menu dropdown-navbar" id="user_menu">
                        <li class="nav-header">
                            <i class="icon-time"></i>
                            最近登录于${shiro.principal().lastLoginTime?time}
                        </li>
                        <li class="divider visible-phone"></li>
                        <li class="visible-phone">
                            <a href="/message.html">
                                <i class="icon-envelope"></i>
                                消息
                                <span class="badge badge-success message_count"></span>
                            </a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="${ctx}/action/logout">
                                <i class="icon-off"></i>
                                登出
                            </a>
                        </li>
                    </ul>
                    <!-- BEGIN User Dropdown -->
                </li>
                <!-- END Button User -->
            </ul>
            <!-- END Navbar Buttons -->
        </div><!--/.container-fluid-->
    </div><!--/.navbar-inner-->
</div>
<!-- END Navbar -->
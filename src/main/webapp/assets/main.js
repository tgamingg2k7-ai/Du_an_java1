function toggleSidebar() {
    document.body.classList.toggle("sidebar-collapsed");
}

document.addEventListener("DOMContentLoaded", function () {

    const userBtn = document.getElementById("userBtn");
    const logoutMenu = document.getElementById("logoutMenu");
    const darkBtn = document.getElementById("darkBtn");
    const submenuToggles = document.querySelectorAll(".submenu-toggle");

    // =========================
    // MENU USER / ĐĂNG XUẤT
    // =========================
    if (userBtn && logoutMenu) {
        userBtn.addEventListener("click", function (event) {
            event.stopPropagation();
            logoutMenu.classList.toggle("show");
        });

        document.addEventListener("click", function () {
            logoutMenu.classList.remove("show");
        });

        logoutMenu.addEventListener("click", function (event) {
            event.stopPropagation();
        });
    }

    // =========================
    // DARK MODE
    // =========================
    if (darkBtn) {
        darkBtn.addEventListener("click", function () {
            document.body.classList.toggle("dark");

            if (document.body.classList.contains("dark")) {
                localStorage.setItem("theme", "dark");
            } else {
                localStorage.setItem("theme", "light");
            }
        });

        const savedTheme = localStorage.getItem("theme");

        if (savedTheme === "dark") {
            document.body.classList.add("dark");
        }
    }

    // =========================
    // SUBMENU SIDEBAR
    // =========================
    submenuToggles.forEach(function (toggle) {
        toggle.addEventListener("click", function () {
            const parent = this.closest(".has-submenu");

            if (parent) {
                parent.classList.toggle("open");
            }
        });
    });

    // =========================
    // ACTIVE MENU
    // =========================
    const links = document.querySelectorAll(".sidebar-menu a[href]");
    const currentPath = window.location.pathname;
    const currentSearch = window.location.search;

    links.forEach(function (link) {
        const href = link.getAttribute("href");

        if (!href || href === "javascript:void(0)") {
            return;
        }

        const linkUrl = new URL(link.href);
        const linkPath = linkUrl.pathname;
        const linkSearch = linkUrl.search;

        let isActive = false;

        // Trang /product
        if (currentPath === linkPath && linkSearch === "") {
            isActive = true;
        }

        // Trang có action, ví dụ /attribute?action=color
        if (currentPath === linkPath && currentSearch === linkSearch && linkSearch !== "") {
            isActive = true;
        }

        if (isActive) {
            link.classList.add("active-link");

            const parentSubmenu = link.closest(".has-submenu");
            if (parentSubmenu) {
                parentSubmenu.classList.add("open");
            }
        }
    });

});
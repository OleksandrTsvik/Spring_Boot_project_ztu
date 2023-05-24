(function () {
    const menus = document.querySelectorAll('.menu__draggable');

    menus.forEach((menu) => {
        const list = menu.querySelector('.draggable__list');

        const iconLeft = menu.querySelector('.draggable__icon-left');
        const iconRight = menu.querySelector('.draggable__icon-right');

        let isDragging = false, prevTouch;

        iconLeft.addEventListener('click', () => handleIcons(list, iconLeft, iconRight, list.scrollLeft += -350));
        iconRight.addEventListener('click', () => handleIcons(list, iconLeft, iconRight, list.scrollLeft += 350));

        if (list.scrollWidth <= list.clientWidth) {
            iconRight.style.display = 'none';
        }

        function dragStart(event) {
            isDragging = true;

            if (event.touches) {
                prevTouch = event.touches[0];
            }
        }

        function dragging(event) {
            if (!isDragging) {
                return;
            }

            list.classList.add('dragging');

            if (event.movementX) {
                list.scrollLeft -= event.movementX;
            }

            if (event.touches) {
                list.scrollLeft -= event.touches[0].pageX - prevTouch.pageX;

                prevTouch = event.touches[0];
            }

            handleIcons(list, iconLeft, iconRight, list.scrollLeft);
        }

        function dragStop() {
            isDragging = false;
            prevTouch = null;
            list.classList.remove('dragging');
        }

        list.addEventListener('mousedown', dragStart);
        list.addEventListener('mousemove', dragging);
        document.addEventListener('mouseup', dragStop);

        list.addEventListener('touchstart', dragStart);
        list.addEventListener('touchmove', dragging);
        list.addEventListener('touchend', dragStop);
    });

    function handleIcons(menu, iconLeft, iconRight, scrollVal) {
        let maxScrollableWidth = menu.scrollWidth - menu.clientWidth;

        iconLeft.style.display = scrollVal <= 0 ? 'none' : 'flex';
        iconRight.style.display = maxScrollableWidth > scrollVal ? 'flex' : 'none';
    }
})();
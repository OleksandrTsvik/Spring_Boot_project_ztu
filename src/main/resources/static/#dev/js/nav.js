(function () {
    const btnBurger = document.querySelector('.menu__burger');
    const menuBody = document.querySelector('.menu__body');

    if (!btnBurger || !menuBody) {
        return;
    }

    btnBurger.addEventListener('click', () => {
        btnBurger.classList.toggle('active');
        menuBody.classList.toggle('active');
        document.body.classList.toggle('lock');
    });
})();
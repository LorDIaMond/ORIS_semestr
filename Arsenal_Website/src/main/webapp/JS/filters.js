document.addEventListener('DOMContentLoaded', function () {

    const allCheckboxes = document.querySelectorAll('input[type="checkbox"]');

    allCheckboxes.forEach(checkbox => {
        checkbox.addEventListener('change', function () {

            const selectedCategories = Array.from(
                document.querySelectorAll('input[id^="cat_"]:checked')
            ).map(cb => cb.value);

            const selectedPriceRanges = Array.from(
                document.querySelectorAll('input[id^="price_"]:checked')
            ).map(cb => cb.value);

            const url = new URL(window.location.href);
            url.searchParams.set('ajax', 'true');

            selectedCategories.forEach(id => url.searchParams.append('categories', id));
            selectedPriceRanges.forEach(range => url.searchParams.append('price', range));

            fetch(url)
                .then(response => {
                    if (!response.ok) throw new Error('Ошибка');
                    return response.text();
                })
                .then(html => {
                    document.getElementById('product-cards-container').innerHTML = html;
                })
                .catch(error => {
                    console.error('Filter error:', error);
                    document.getElementById('product-cards-container').innerHTML =
                        '<p class="error">Failed to load products. Please try again.</p>';
                });
        });
    });
});
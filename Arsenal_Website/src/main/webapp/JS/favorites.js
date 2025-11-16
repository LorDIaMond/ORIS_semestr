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

            const url = new URL(window.location.origin + window.location.pathname);
            url.searchParams.set('ajax', 'true');
            selectedCategories.forEach(id => url.searchParams.append('categories', id));
            selectedPriceRanges.forEach(range => url.searchParams.append('price', range));

            fetch(url)
                .then(response => {
                    if (!response.ok) throw new Error('Filter failed');
                    return response.text();
                })
                .then(html => {
                    document.getElementById('product-cards-container').innerHTML = html;
                    attachFavoriteHandlers();
                    attachCartHandlers();
                })
                .catch(error => {
                    console.error('Filter error:', error);
                    document.getElementById('product-cards-container').innerHTML =
                        '<p class="error">Failed to load products.</p>';
                });
        });
    });

    attachFavoriteHandlers();
    attachCartHandlers();
});

// Функция для обработки сердечек
function attachFavoriteHandlers() {
    document.querySelectorAll('.favorite-btn').forEach(btn => {
        const clone = btn.cloneNode(true);
        btn.parentNode.replaceChild(clone, btn);

        clone.addEventListener('click', function (event) {
            event.preventDefault();
            const card = this.closest('.product-card');
            const productId = card.dataset.productId;
            const isCurrentlyFavorite = this.dataset.favorite === 'true';

            fetch(window.location.origin + window.location.pathname, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: `action=addToFavorites&productId=${productId}`
            })
                .then(response => {
                    if (response.status === 401) {
                        alert('Please sign in to save favorites.');
                        return;
                    }
                    if (!response.ok) throw new Error('Error');
                    return response.json();
                })
                .then(data => {
                    this.dataset.favorite = data.favorite;
                    this.textContent = data.favorite ? '❤️' : '🤍';
                })
                .catch(error => {
                    console.error('Favorite error:', error);
                    alert('Failed to update favorites.');
                });
        });
    });
}

// Функция для обработки корзины
function attachCartHandlers() {
    console.log("🔍 attachCartHandlers called");
    document.querySelectorAll('.cart-btn').forEach(btn => {
        const clone = btn.cloneNode(true);
        btn.parentNode.replaceChild(clone, btn);

        clone.addEventListener('click', function (event) {
            event.preventDefault();
            const card = this.closest('.product-card');
            const productId = card.dataset.productId;
            const isInCart = this.dataset.inCart === 'true';

            if (!productId) {
                console.error("❌ Product ID is missing!");
                alert("Failed to add to cart: product ID not found.");
                return;
            }

            fetch(window.location.origin + window.location.pathname, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: `action=addToCart&productId=${productId}`
            })
                .then(response => {
                    if (response.status === 401) {
                        alert('Please sign in to add to cart.');
                        return;
                    }
                    if (!response.ok) throw new Error('Error');
                    return response.json();
                })
                .then(data => {
                    this.dataset.inCart = data.inCart;
                    this.textContent = data.inCart ? '🛒 In Cart' : '➕ Add to Cart';
                    this.style.backgroundColor = data.inCart ? '#28a745' : '#C80815';
                })
                .catch(error => {
                    console.error('Cart error:', error);
                    alert('Failed to update cart.');
                });
        });
    });
}
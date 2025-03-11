document.addEventListener("DOMContentLoaded", function () {
    loadRandomBooks(); // Load 5 random books when the page loads
});

function loadRandomBooks() {
    fetch(`/api/books/random?count=4`) // Fetch 5 random books from the backend
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to fetch random books");
            }
            return response.json();
        })
        .then(books => {
            if (!Array.isArray(books)) {
                throw new Error("Invalid data format received");
            }
            displayBooks(books);
        })
        .catch(error => {
            console.error("Error fetching random books:", error);
            document.getElementById("searchResults").innerHTML =
                "<p class='text-danger'>Error loading books. Please try again later.</p>";
        });
}

function searchBooks() {
    let field = document.getElementById("searchField").value;
    let value = document.getElementById("searchValue").value.trim();
    let sortOrder = document.getElementById("sortOrder").value; // Get sort order (asc/desc)

    // If no search term is entered, show random books instead
    if (value === "") {
        loadRandomBooks();
        return;
    }

    fetch(`/api/books/search?field=${field}&value=${encodeURIComponent(value)}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to fetch search results");
            }
            return response.json();
        })
        .then(books => {
            if (!Array.isArray(books)) {
                throw new Error("Invalid data format received");
            }

            // Sort books based on search field and sort order
            books.sort((a, b) => {
                let fieldA = a[field]?.toString().toLowerCase() || "";
                let fieldB = b[field]?.toString().toLowerCase() || "";
                return sortOrder === "asc"
                    ? fieldA.localeCompare(fieldB, undefined, {numeric: true})
                    : fieldB.localeCompare(fieldA, undefined, {numeric: true});
            });

            displayBooks(books);
        })
        .catch(error => {
            console.error("Error fetching books:", error);
            document.getElementById("searchResults").innerHTML =
                "<p class='text-danger'>Error fetching books. Please try again later.</p>";
        });
}

function displayBooks(books) {
    let resultDiv = document.getElementById("searchResults");
    resultDiv.innerHTML = ""; // Clear previous results

    if (!books.length) {
        resultDiv.innerHTML = "<p class='text-warning'>No books found.</p>";
        return;
    }

    let resultHTML = books.map(book => {
        return `
                <div class="col-lg-6" data-book-id="${book.bookId}">
                    <div class="card mb-4">
                        <a href="#!"><img class="card-img-top" src="${book.imageUrl || "https://dummyimage.com/700x350/dee2e6/6c757d.jpg"}" alt="Book Cover" /></a>
                        <div class="card-body">
                            <div class="small text-muted">Published: ${book.publicationYear || "Unknown"}</div>
                            <h2 class="card-title h4">${book.title || "Untitled"}</h2>
                            <p class="card-text" style="margin-bottom: 5px; line-height: 1.2;"><strong>Book Id:</strong> ${book.bookId || "Unknown"}</p>
                            <p class="card-text" style="margin-bottom: 5px; line-height: 1.2;"><strong>Author:</strong> ${book.author || "Unknown"}</p>
                            <p class="card-text" style="margin-bottom: 5px; line-height: 1.2;"><strong>Genre:</strong> ${book.genre || "Unknown"}</p>
                            <p class="card-text" style="margin-bottom: 5px; line-height: 1.2;"><strong>Available Copies:</strong> ${book.count || 0}</p>
                        </div>
                    </div>
                </div>`;
    }).join("");
    resultDiv.innerHTML = resultHTML;
}
function submitForm(action) {
    var form = document.getElementById('bookForm');
    if (action === 'borrow') {
        form.action = '/library/admin/borrowBook';
    } else if (action === 'return') {
        form.action = '/library/admin/returnBook';
    }
    form.submit();
}
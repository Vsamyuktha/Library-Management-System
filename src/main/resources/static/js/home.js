<!-- Search Books JavaScript -->
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

    const reservedBooksData = document.getElementById("reserved-books").dataset.reservations;
    const reservedBookIds = new Set(JSON.parse(reservedBooksData));

    let resultHTML = books.map(book => {
    let isReserved = reservedBookIds.has(book.bookId); // Check if book is reserved
    let reserveButtonClass = isReserved ? "btn-secondary disabled" : "btn-success";
    let reserveButtonText = isReserved ? "Reserved" : "Reserve →";
    let reserveButtonDisabled = isReserved ? "disabled" : "";

    return `
                <div class="col-lg-6" data-book-id="${book.bookId}">
                    <div class="card mb-4">
                        <a href="#!"><img class="card-img-top" src="https://dummyimage.com/700x350/dee2e6/6c757d.jpg" alt="Book Cover" /></a>
                        <div class="card-body">
                            <div class="small text-muted">Published: ${book.publicationYear || "Unknown"}</div>
                            <h2 class="card-title h4">${book.title || "Untitled"}</h2>
                            <p class="card-text" style="margin-bottom: 5px; line-height: 1.2;"><strong>Author:</strong> ${book.author || "Unknown"}</p>
                            <p class="card-text" style="margin-bottom: 5px; line-height: 1.2;"><strong>Genre:</strong> ${book.genre || "Unknown"}</p>
                            <p class="card-text" style="margin-bottom: 5px; line-height: 1.2;"><strong>Available Copies:</strong> ${book.count || 0}</p>
                            <button class="btn ${reserveButtonClass}" onclick="reserveBook('${book.bookId}')" ${reserveButtonDisabled}>${reserveButtonText}</button>
                        </div>
                    </div>
                </div>`;
    }).join("");
    resultDiv.innerHTML = resultHTML;
}

function reserveBook(bookId) {
    const bookIdLong = Number(bookId);
    const username = document.getElementById("username").innerText;
    fetch(`/library/reservations/reserveBook/${username}/${bookIdLong}`, {
        method: 'POST',
        headers: {
        'Content-Type': 'application/json'
        },
        })
    .then(response => response.json())
    .then(reservation => {
    if (reservation.status === 'COMPLETED') {
        alert('Book reserved successfully!');
        window.location.reload(true);
    } else if (reservation.status === 'PENDING') {
        alert('Book is currently unavailable. You will be notified when it becomes available.');
    } else {
        alert('Reservation status: ' + reservation.status);
    }
    })
        .catch(error => {
            console.error('Error reserving book:', error);
            alert('Error reserving book. Please try again.');
    });
}

function markAsRead(button) {
    let notificationId = button.getAttribute("data-id");

    fetch('/library/notifications/read/' + notificationId, {
        method: 'GET'
    })
    .then(response => {
        if (response.ok) {
            console.log("Notifications marked read!");
            document.getElementById('notification-' + notificationId).remove(); // Remove row from table
        } else {
            console.error('Failed to mark notification as read');
        }
    })
    .catch(error => console.error('Error:', error));
}

function markAllAsRead() {
    const username = document.getElementById("username").innerText;
    fetch('/library/notifications/mark-all-read/' + username, {
        method: 'GET'
    })
    .then(response => {
        if (response.ok) {
            console.log("Marked all notifications as read!");
            const notificationsTableBody = document.getElementById("notifications-body");
            if(notificationsTableBody) {
                notificationsTableBody.innerHTML = "";  // Clear all notifications
            }
        } else {
            console.error('Failed to mark all notifications as read');
        }
    })
    .catch(error => console.error('Error:', error));
}
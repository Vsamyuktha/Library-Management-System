document.addEventListener("DOMContentLoaded", function () {
    loadAmountDue();
    loadBorrowedBooks();
});

function searchBooks() {
    let field = document.getElementById("searchField").value;
    let value = document.getElementById("searchValue").value.trim();

    if (!value) {
        alert("Please enter a search term!");
        return;
    }

    fetch(`/api/books/search?field=${field}&value=${encodeURIComponent(value)}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Network response was not ok");
            }
            return response.json();
        })
        .then(books => {
            let resultDiv = document.getElementById("searchResults");
            resultDiv.innerHTML = ""; // Clear previous results

            if (books.length === 0) {
                resultDiv.innerHTML = "<p>No books found.</p>";
                return;
            }

            let resultHTML = "";
            books.forEach(book => {
                let reserveButtonClass = "btn-success";
                let reserveButtonText = "Reserve →";

                resultHTML += `
                        <div class="col-lg-6">
                            <div class="card mb-4">
                                <a href="#!"><img class="card-img-top" src="https://dummyimage.com/700x350/dee2e6/6c757d.jpg" alt="Book Cover" /></a>
                                <div class="card-body">
                                    <div class="small text-muted">Published: ${book.publicationYear}</div>
                                    <h2 class="card-title h4">${book.title}</h2>
                                    <p class="card-text"><strong>Author:</strong> ${book.author}</p>
                                    <p class="card-text"><strong>Genre:</strong> ${book.genre}</p>
                                    <p class="card-text"><strong>Available Copies:</strong> ${book.count}</p>
                                    <a class="btn ${reserveButtonClass}" href="#!">${reserveButtonText}</a>
                                </div>
                            </div>
                        </div>`;
            });

            resultDiv.innerHTML = resultHTML;
        })
        .catch(error => {
            console.error("Error fetching books:", error);
            document.getElementById("searchResults").innerHTML = "<p>Error fetching books. Please try again.</p>";
        });
}

function loadReservedBooks() {
    const username = document.getElementById("username").innerText;
    fetch(`/library/reservations/listAllReservations/${username}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(reservations => {
            const reservedBooksTableElement = document.getElementById('reservedBooksTable');
            if (reservations.length === 0) {
                reservedBooksTableElement.textContent = 'No books currently reserved.';
            } else {
                let tableHTML = `
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>Book Title</th>
                                <th>Author</th>
                                <th>Reservation Date</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                `;
                reservations.forEach(reservation => {
                    tableHTML += `
                        <tr>
                            <td>${reservation.book.title}</td>
                            <td>${reservation.book.author}</td>
                            <td>${new Date(reservation.dateTimeReserved).toLocaleString()}</td>
                            <td>${reservation.status}</td>
                        </tr>
                    `;
                });
                tableHTML += `
                        </tbody>
                    </table>
                `;
                reservedBooksTableElement.innerHTML = tableHTML;
            }
        })
        .catch(error => {
            console.error('Error fetching reservations:', error);
            document.getElementById('reservedBooksTable').textContent = 'Error loading reservations. Please try again.';
        });
}

// Call the function when the page loads
document.addEventListener('DOMContentLoaded', loadReservedBooks);


function loadBorrowedBooks() {
    const username = document.getElementById("username").innerText;
    fetch(`/library/reservations/listAllBorrows/${username}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(borrows => {
            const borrowedBooksTableElement = document.getElementById('borrowedBooksTable');
            if (borrows.length === 0) {
                borrowedBooksTableElement.textContent = 'No books currently borrowed.';
            } else {
                let tableHTML = `
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>Book Title</th>
                            <th>Author</th>
                            <th>Borrow Date</th>
                            <th>Due Date</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
            `;
                borrows.forEach(borrow => {
                    tableHTML += `
                    <tr>
                        <td>${borrow.book.title}</td>
                        <td>${borrow.book.author}</td>
                        <td>${new Date(borrow.dateTimeBorrowed).toLocaleString()}</td>
                        <td>${new Date(borrow.dateTimeDue).toLocaleString()}</td>
                        <td>${borrow.status}</td>
                    </tr>
                `;
                });
                tableHTML += `
                    </tbody>
                </table>
            `;
                borrowedBooksTableElement.innerHTML = tableHTML;
            }
        })
        .catch(error => {
            console.error('Error fetching borrowed books:', error);
            document.getElementById('borrowedBooksTable').textContent = 'Error loading borrowed books. Please try again.';
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
                console.log("Marked all notifications as read!")
                const notificationsTableBody = document.getElementById("notifications-body")
                if (notificationsTableBody) {
                    notificationsTableBody.innerHTML = "";  // Clear all notifications
                }
            } else {
                console.error('Failed to mark all notifications as read');
            }
        })
        .catch(error => console.error('Error:', error));
}

function loadAmountDue() {
    let username = document.getElementById("username").innerText.trim();
    fetch(`/library/reservations/amountDue/${username}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to fetch amount due");
            }
            return response.json();
        })
        .then(amount => {
            document.getElementById("amountDue").innerText = `$${amount.toFixed(2)}`;
        })
        .catch(error => {
            console.error("Error fetching amount due:", error);
            document.getElementById("amountDue").innerText = "$0.00";
        });
}

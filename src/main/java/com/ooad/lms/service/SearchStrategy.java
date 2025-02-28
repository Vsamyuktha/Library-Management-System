package com.ooad.lms.service;

import com.ooad.lms.entity.Book;

import java.util.List;
import java.util.stream.Collectors;

// Strategy Pattern Interface
public interface SearchStrategy {
    List<Book> search(List<Book> books, String value);

    // **Title Search Strategy**
    class TitleSearchStrategy implements SearchStrategy {
        @Override
        public List<Book> search(List<Book> books, String title) {
            return books.stream()
                    .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                    .collect(Collectors.toList());
        }
    }

    // **Author Search Strategy**
    class AuthorSearchStrategy implements SearchStrategy {
        @Override
        public List<Book> search(List<Book> books, String author) {
            return books.stream()
                    .filter(book -> book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                    .collect(Collectors.toList());
        }
    }

    // **Genre Search Strategy**
    class GenreSearchStrategy implements SearchStrategy {
        @Override
        public List<Book> search(List<Book> books, String genre) {
            return books.stream()
                    .filter(book -> book.getGenre().toLowerCase().contains(genre.toLowerCase()))
                    .collect(Collectors.toList());
        }
    }

    // **Year Search Strategy**
    class YearSearchStrategy implements SearchStrategy {
        @Override
        public List<Book> search(List<Book> books, String year) {
            int publicationYear = Integer.parseInt(year);
            return books.stream()
                    .filter(book -> book.getPublicationYear() == publicationYear)
                    .collect(Collectors.toList());
        }
    }

    // **Search Context Class (Manages the Strategy)**
    class SearchContext {
        private SearchStrategy strategy;

        public void setStrategy(SearchStrategy strategy) {
            this.strategy = strategy;
        }

        public List<Book> executeSearch(List<Book> books, String value) {
            return strategy.search(books, value);
        }
    }
}

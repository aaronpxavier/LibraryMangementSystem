package com.smoothstack.lms.service;

import com.smoothstack.lms.entity.Author;
import com.smoothstack.lms.entity.Book;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

import com.smoothstack.lms.entity.Genre;
import com.smoothstack.lms.entity.Publisher;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class GoogleBooksService {
    private final String BASE_URL = "https://www.googleapis.com/books/v1/volumes?";
    private final String API_KEY = new AccessCredentials().getApiKey();
    private final String BOOK_SEARCH_PARAMS = "&maxResults=20&langRestrict=en&orderBy=relevance&key=" + API_KEY;
    private final String OPEN_LIB_BASE_URL = "https://openlibrary.org/api/books?bibkeys=ISBN:";
    private final String PUB_SEARCH_PARAMS = "&jscmd=data&format=json";

    private JSONObject getRequest (String url) {
        HttpGet request = null;
        request = new HttpGet(url);
        final CloseableHttpClient httpClient = HttpClients.createDefault();

        request.addHeader(HttpHeaders.USER_AGENT, "JAVA");
        request.addHeader(HttpHeaders.ACCEPT, "application/json");
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();
            String jsonString = IOUtils.toString(entity.getContent());
            //System.out.println(jsonString);
            return (JSONObject) new JSONParser().parse(jsonString);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Author> parseAuthors(JSONObject volumeInfo) {
        List<Author> authorsList = new ArrayList<>();
        // parse authors
        if (volumeInfo.containsKey("authors")) {
            ArrayList<String> tempAuthorList = (ArrayList<String>) volumeInfo.get("authors");
            Iterator<String> iterator = tempAuthorList.iterator();
            while(iterator.hasNext()) {
                authorsList.add(new Author(iterator.next()));
            }

        }
        return authorsList;
    }

    private String parseISBN(JSONObject volumeInfo, Book book) {
        String isbn = null;
        if (volumeInfo.containsKey("industryIdentifiers")) {
            JSONArray jsonArray = (JSONArray) volumeInfo.get("industryIdentifiers");
            Iterator<JSONObject> iterator = jsonArray.iterator();
            while(iterator.hasNext()) {
                JSONObject tempJsonObj = iterator.next();
                if (tempJsonObj.containsKey("type") && ((String) tempJsonObj.get("type")).compareTo("ISBN_13") == 0) {
                    isbn = (String) tempJsonObj.get("identifier");
                } else if (tempJsonObj.containsKey("type") && "ISBN_10".compareTo((String) tempJsonObj.get("type")) == 0 && book.getIsbn() == null) {
                    isbn = (String) tempJsonObj.get("identifier");
                }
            }
        }
        return isbn;
    }

    private String parsePublisher(JSONObject volumeInfo, Book book) {
        String publisher = volumeInfo.containsKey("publisher") ? (String)volumeInfo.get("publisher") : null;
        return publisher == null || publisher.compareTo("") == 0 ? null : publisher;
    }

    private List<Genre> parseGenres(JSONObject volumeInfo, Book book) {
        List<Genre> genres = new ArrayList<>();
        if(volumeInfo.containsKey("categories")) {
            JSONArray jsonArray = (JSONArray) volumeInfo.get("categories");
            for(Object item : jsonArray) {
                genres.add(new Genre((String) item));
            }
        }
        return genres;
    }

    private Book parseBookVolume (JSONObject jsonObject) {
        JSONObject volumeInfo = (JSONObject) jsonObject.get("volumeInfo");
        Book book = new Book((String) volumeInfo.get("title"));
        List<Author> authorsList = parseAuthors(volumeInfo);
        String isbn = parseISBN(volumeInfo, book);
        String publisher = parsePublisher(volumeInfo, book);
        List<Genre> genres = parseGenres(volumeInfo, book);
        book.setGenres(genres.isEmpty() ? null : genres);
        book.setAuthors(authorsList.isEmpty() ? null : authorsList);
        book.setIsbn(isbn == null ? null : isbn);
        book.setPublisher(publisher == null ? null : new Publisher(publisher));
        return book;
    }

    public String publihserAddress(String isbn) {
        if(isbn == null)
            return null;
        JSONObject jObject;
        System.out.println("Getting Address information for isbn " + isbn);
        jObject = getRequest(OPEN_LIB_BASE_URL + isbn + PUB_SEARCH_PARAMS);
        if(jObject.containsKey("ISBN:" + isbn))
            jObject = (JSONObject) jObject.get("ISBN:" + isbn);
        if(jObject.containsKey("publish_places")) {
            JSONArray placesArray = (JSONArray) jObject.get("publish_places");
            if (placesArray.isEmpty())
                return null;
            JSONObject place = (JSONObject) placesArray.get(0);
            System.out.println(place.get("name"));
            return (String)place.get("name");
        }
        return null;
    }

    public LinkedList<Book> booksSearch(String search)  {
        String urlString = BASE_URL + "q=intitle:" + URLEncoder.encode(search) + BOOK_SEARCH_PARAMS;
        LinkedList<Book> bookList = new LinkedList<>();
        Iterator<JSONObject> iterator;
        JSONArray jsonArray;
        System.out.println("Retreiving books from " + urlString);
        jsonArray = (JSONArray) getRequest(urlString).get("items");
        if (jsonArray == null)
            return null;
        iterator = jsonArray.iterator();
        while (iterator.hasNext())
            bookList.add(parseBookVolume(iterator.next()));
        return bookList;
    }
}

/*
 * System.out.println(bookList.getLast().getTitle());
 *             System.out.println(bookList.getLast().getIsbn());
 *             System.out.println(bookList.getLast().getAuthors().get(0).getAuthorName());
 *             System.out.println();
 */

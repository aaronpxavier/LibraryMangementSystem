package com.smoothstack.lms.service;

import com.smoothstack.lms.entity.Author;
import com.smoothstack.lms.entity.Book;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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
    private Book parseBookVolume (JSONObject jsonObject) {
        JSONObject volumeInfo = (JSONObject) jsonObject.get("volumeInfo");
        Book book = new Book((String) volumeInfo.get("title"));
        List<Author> authorsList = parseAuthors(volumeInfo);
        String isbn = parseISBN(volumeInfo, book);
        if (!authorsList.isEmpty()) book.setAuthors(authorsList);
        if(isbn != null) book.setIsbn(isbn);
        return book;
    }

    public LinkedList<Book> booksSearch(String search)  {
        String urlString = BASE_URL + "q=intitle:" + URLEncoder.encode(search) + BOOK_SEARCH_PARAMS;
        LinkedList<Book> bookList = new LinkedList<>();
        JSONArray jsonArray = (JSONArray) getRequest(urlString).get("items");
        Iterator<JSONObject> iterator = jsonArray.iterator();
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

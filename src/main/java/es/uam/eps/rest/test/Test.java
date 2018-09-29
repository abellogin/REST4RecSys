package es.uam.eps.rest.test;

import es.uam.eps.rest.model.Event;
import es.uam.eps.rest.model.Item;
import es.uam.eps.rest.model.User;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.Assert.*;

/**
 *
 * @author Ivan Garcia
 * @author Alejandro Bellogin
 */
public class Test {

    public static String runTest(String url, String data, String usePost, PrintStream out) {
        long time = System.currentTimeMillis();
        int ok = 0;
        int error = 0;
        int other = 0;
        try {
            HttpURLConnection servletConnection = (HttpURLConnection) new URL(url).openConnection();
            if (usePost.equals("post")) {
                servletConnection.setDoOutput(true);
                servletConnection.setRequestMethod("POST");
                servletConnection.setRequestProperty("Content-Type", "application/json");
                OutputStreamWriter writer = new OutputStreamWriter(servletConnection.getOutputStream());
                writer.write(data);
                writer.flush();
                writer.close();
            } else if (usePost.equals("get")) {
                servletConnection.setDoOutput(false);
                servletConnection.setRequestMethod("GET");
            } else if (usePost.equals("delete")) {
                servletConnection.setDoOutput(false);
                servletConnection.setRequestMethod("DELETE");
            }
            InputStream response = servletConnection.getInputStream();
            if (out != null) {
                BufferedInputStream in = new BufferedInputStream(response);
                byte[] contents = new byte[1024];

                int bytesRead = 0;
                String strFileContents = "";
                while ((bytesRead = in.read(contents)) != -1) {
                    strFileContents += new String(contents, 0, bytesRead);
                }

                out.println(strFileContents);
                return strFileContents;
            }
        } catch (IOException e) {
            error++;
        }
        return null;
    }

    public static void testAddUser(User user, String data, File file, FileOutputStream fileOutputStream, PrintStream printStream) throws Exception {
        String responsePost = runTest("http://localhost:8080/user/add/", data, "post", printStream);
        String responseGet = runTest("http://localhost:8080/user/get/" + responsePost, "", "get", printStream);
        user = new ObjectMapper().readValue(responseGet, User.class);
        assertEquals(user.getUid(), responsePost);
    }

    public static void testAddUserFail(User user, String data, File file, FileOutputStream fileOutputStream, PrintStream printStream) throws Exception {
        user = null;
        data = new ObjectMapper().writeValueAsString(user);
        String responsePost = runTest("http://localhost:8080/user/add/", data, "post", printStream);
        assertEquals("-1", responsePost);
        user = new User();
        user.setName("test");
        user.setLocation("testLocation");
        data = new ObjectMapper().writeValueAsString(user);
        responsePost = runTest("http://localhost:8080/user/add/", data, "post", printStream);
        user.setUid(responsePost);
        data = new ObjectMapper().writeValueAsString(user);
        responsePost = runTest("http://localhost:8080/user/add/", data, "post", printStream);
        assertEquals("-1", responsePost);
    }

    public static void testGetUser(User user, String data, File file, FileOutputStream fileOutputStream, PrintStream printStream) throws Exception {
        String responsePost = runTest("http://localhost:8080/user/add/", data, "post", printStream);
        String responseGet = runTest("http://localhost:8080/user/get/" + responsePost, "", "get", printStream);
        user = new ObjectMapper().readValue(responseGet, User.class);
        assertEquals(user.getUid(), responsePost);
    }

    public static void testDeleteUser(User user, String data, File file, FileOutputStream fileOutputStream, PrintStream printStream) throws Exception {
        String responsePost = runTest("http://localhost:8080/user/add/", data, "post", printStream);
        String responseDelete = runTest("http://localhost:8080/user/delete/" + responsePost, "", "delete", printStream);
        assertEquals("OK", responseDelete);
        String responseGet = runTest("http://localhost:8080/user/get/" + responsePost, "", "get", printStream);
        assertEquals("", responseGet);
    }

    public static void testAddItem(Item item, String data, File file, FileOutputStream fileOutputStream, PrintStream printStream) throws Exception {
        String responsePost = runTest("http://localhost:8080/item/add/", data, "post", printStream);
        String responseGet = runTest("http://localhost:8080/item/get/" + responsePost, "", "get", printStream);
        item = new ObjectMapper().readValue(responseGet, Item.class);
        assertEquals(responsePost, item.getIid());
    }

    public static void testAddItemFail(Item item, String data, File file, FileOutputStream fileOutputStream, PrintStream printStream) throws Exception {
        item = null;
        data = new ObjectMapper().writeValueAsString(item);
        String responsePost = runTest("http://localhost:8080/item/add/", data, "post", printStream);
        assertEquals("-1", responsePost);
        item = new Item();
        item.setName("test");
        item.setContent("testContenido");
        data = new ObjectMapper().writeValueAsString(item);
        responsePost = runTest("http://localhost:8080/item/add/", data, "post", printStream);
        item.setIid(responsePost);
        data = new ObjectMapper().writeValueAsString(item);
        responsePost = runTest("http://localhost:8080/item/add/", data, "post", printStream);
        assertEquals("-1", responsePost);
    }

    public static void testGetItem(Item item, String data, File file, FileOutputStream fileOutputStream, PrintStream printStream) throws Exception {
        String responsePost = runTest("http://localhost:8080/item/add/", data, "post", printStream);
        String responseGet = runTest("http://localhost:8080/item/get/" + responsePost, "", "get", printStream);
        item = new ObjectMapper().readValue(responseGet, Item.class);
        assertEquals(item.getIid(), responsePost);
    }

    public static void testDeleteItem(Item item, String data, File file, FileOutputStream fileOutputStream, PrintStream printStream) throws Exception {
        String responsePost = runTest("http://localhost:8080/item/add/", data, "post", printStream);
        String responseDelete = runTest("http://localhost:8080/item/delete/" + responsePost, "", "delete", printStream);
        assertEquals("OK", responseDelete);
        String responseGet = runTest("http://localhost:8080/item/get/" + responsePost, "", "get", printStream);
        assertEquals("", responseGet);
    }

    public static void testAddEventRating(Event event, String data, File file, FileOutputStream fileOutputStream, PrintStream printStream) throws Exception {
        User user = new User();
        user.setName("test");
        user.setLocation("testLocation");
        String dataUser = new ObjectMapper().writeValueAsString(user);
        String userId = runTest("http://localhost:8080/user/add/", dataUser, "post", printStream);
        Item item = new Item();
        item.setName("test");
        item.setContent("testContenido");
        String dataItem = new ObjectMapper().writeValueAsString(item);
        String itemId = runTest("http://localhost:8080/item/add/", dataItem, "post", printStream);
        event.setUid(userId);
        event.setIid(itemId);
        event.setType(Event.RATING_TYPE);
        event.setValue(4.0);
        data = new ObjectMapper().writeValueAsString(event);
        String responsePost = runTest("http://localhost:8080/event/add/", data, "post", printStream);
        String responseGet = runTest("http://localhost:8080/event/get/" + responsePost, "", "get", printStream);
        event = new ObjectMapper().readValue(responseGet, Event.class);
        assertEquals(responsePost, event.getEid());
    }

    public static void testAddEventCount(Event event, String data, File file, FileOutputStream fileOutputStream, PrintStream printStream) throws Exception {
        User user = new User();
        user.setName("test");
        user.setLocation("testLocation");
        String dataUser = new ObjectMapper().writeValueAsString(user);
        String userId = runTest("http://localhost:8080/user/add/", dataUser, "post", printStream);
        Item item = new Item();
        item.setName("test");
        item.setContent("testContenido");
        String dataItem = new ObjectMapper().writeValueAsString(item);
        String itemId = runTest("http://localhost:8080/item/add/", dataItem, "post", printStream);
        event.setUid(userId);
        event.setIid(itemId);
        event.setType(Event.COUNT_TYPE);
        data = new ObjectMapper().writeValueAsString(event);
        String responsePost = runTest("http://localhost:8080/event/add/", data, "post", printStream);
        String responseGet = runTest("http://localhost:8080/event/get/" + responsePost, "", "get", printStream);
        event = new ObjectMapper().readValue(responseGet, Event.class);
        assertEquals(responsePost, event.getEid());
    }

    public static void testAddEventFail(Event event, String data, File file, FileOutputStream fileOutputStream, PrintStream printStream) throws Exception {
        event = new Event();
        data = new ObjectMapper().writeValueAsString(event);
        String responsePost = runTest("http://localhost:8080/event/add/", data, "post", printStream);
        assertEquals("-1", responsePost);
        User user = new User();
        user.setName("test");
        user.setLocation("testLocation");
        String dataUser = new ObjectMapper().writeValueAsString(user);
        String userId = runTest("http://localhost:8080/user/add/", dataUser, "post", printStream);
        event.setUid(userId);
        data = new ObjectMapper().writeValueAsString(event);
        responsePost = runTest("http://localhost:8080/event/add/", data, "post", printStream);
        assertEquals("-1", responsePost);
        Item item = new Item();
        item.setName("test");
        item.setContent("testContenido");
        String dataItem = new ObjectMapper().writeValueAsString(item);
        String itemId = runTest("http://localhost:8080/item/add/", dataItem, "post", printStream);
        event.setIid(itemId);
        event.setType(Event.RATING_TYPE);
        data = new ObjectMapper().writeValueAsString(event);
        responsePost = runTest("http://localhost:8080/event/add/", data, "post", printStream);
        responsePost = runTest("http://localhost:8080/event/add/", data, "post", printStream);
        assertEquals("-1", responsePost);
    }

    public static void main(String args[]) throws Exception {
        ////////////////////// USER //////////////////////
        User user = new User();
        user.setName("test");
        user.setLocation("testLocation");
        String data = new ObjectMapper().writeValueAsString(user);
        System.out.println(data);
        File file = new File("file.txt");
        FileOutputStream fileOutputStream = null;
        PrintStream printStream = null;
        fileOutputStream = new FileOutputStream(file);
        printStream = new PrintStream(fileOutputStream);

        System.out.println("Test añadir usuario: ");
        Test.testAddUser(user, data, file, fileOutputStream, printStream);
        System.out.println("OK\n");
        System.out.println("Test añadir usuario fail: ");
        Test.testAddUserFail(user, data, file, fileOutputStream, printStream);
        System.out.println("OK\n");
        System.out.println("Test obtener usuario: ");
        Test.testGetUser(user, data, file, fileOutputStream, printStream);
        System.out.println("OK\n");
        System.out.println("Test borrar usuario: ");
        Test.testDeleteUser(user, data, file, fileOutputStream, printStream);
        System.out.println("OK\n");

        ////////////////////// ITEM //////////////////////
        Item item = new Item();
        item.setName("test");
        item.setContent("testContenido");
        data = new ObjectMapper().writeValueAsString(item);
        System.out.println(data);

        System.out.println("Test añadir item: ");
        Test.testAddItem(item, data, file, fileOutputStream, printStream);
        System.out.println("OK\n");
        System.out.println("Test añadir item fail: ");
        Test.testAddItemFail(item, data, file, fileOutputStream, printStream);
        System.out.println("OK\n");
        System.out.println("Test obtener item: ");
        Test.testGetItem(item, data, file, fileOutputStream, printStream);
        System.out.println("OK\n");
        System.out.println("Test borrar item: ");
        Test.testDeleteItem(item, data, file, fileOutputStream, printStream);
        System.out.println("OK\n");

        ////////////////////// EVENT //////////////////////
        Event event = new Event();
        data = new ObjectMapper().writeValueAsString(event);
        System.out.println(data);

        System.out.println("Test añadir evento rating: ");
        Test.testAddEventRating(event, data, file, fileOutputStream, printStream);
        System.out.println("OK\n");
        System.out.println("Test añadir evento count: ");
        Test.testAddEventCount(event, data, file, fileOutputStream, printStream);
        System.out.println("OK\n");
        System.out.println("Test añadir evento fail: ");
        Test.testAddEventFail(event, data, file, fileOutputStream, printStream);
        System.out.println("OK\n");
    }
}

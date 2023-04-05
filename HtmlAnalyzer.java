import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class HtmlAnalyzer {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Uso: java HtmlAnalyzer <url>");
            System.exit(1);
        }

        String url = args[0];

        try {
            String html = fetchHtml(url);
            String deepestText = findDeepestNodeText(html);
            System.out.println(deepestText);
        } catch (IOException e) {
            System.out.println("URL connection error");
        }
    }

    //Função que retorna o HTML da página
    private static String fetchHtml(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
    
        // int responseCode = connection.getResponseCode();
        // if (responseCode < 200 || responseCode >= 300) {
        //     throw new IOException("URL connection error");
        // }
    
        try (InputStream inputStream = connection.getInputStream();
             Scanner scanner = new Scanner(inputStream).useDelimiter("\\A")) {
            return scanner.hasNext() ? scanner.next() : "";
        }
    }

    //Função que retorna o texto mais profundo da página
    private static String findDeepestNodeText(String html) {
        String deepestText = "";
        int deepestDepth = -1;

        for (int i = 0; i < html.length(); i++) {
            if (html.charAt(i) == '<') {
                int tagEnd = html.indexOf('>', i);
                if (tagEnd != -1) {
                    String tag = html.substring(i, tagEnd + 1);
                    int nextTagStart = html.indexOf('<', tagEnd + 1);
                    int textStart = tagEnd + 1;
                    int textEnd = nextTagStart != -1 ? nextTagStart : html.length();
                    String text = html.substring(textStart, textEnd).trim();
                    if (!text.isEmpty()) {
                        int depth = getDepth(tag);
                        if (depth > deepestDepth) {
                            deepestText = text;
                            deepestDepth = depth;
                        }
                    }
                    i = textEnd - 1;
                }
            }
        }

        return deepestText;
    }

    //Função que retorna a profundidade de um tag
    private static int getDepth(String tag) {
        int depth = 0;
        for (int i = 0; i < tag.length(); i++) {
            if (tag.charAt(i) == '<') {
                depth++;
            }
        }
        return depth;
    }
}


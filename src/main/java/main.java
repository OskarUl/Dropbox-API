import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.users.FullAccount;
import javax.swing.*;
import java.io.*;

public class main {
    private static final String ACCESS_TOKEN = "gJKkMgm0o8AAAAAAAAAAYSuQZFy2plTSmSAt02gsXVAxn2gTn-qVBaqIMvdZwy8q";

    public static void main(String [] args) throws DbxException {
        byte[] b = {65,66,67,68,69};;

        DbxRequestConfig config = new DbxRequestConfig("dropbox", "en_US");
        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);

        FullAccount account = client.users().getCurrentAccount();
        System.out.println(account.getName().getDisplayName());

        ListFolderResult result = client.files().listFolder("");
        while (true) {
            for (Metadata metadata : result.getEntries()) {
                System.out.println(metadata.getPathLower());
                try {
                    OutputStream downloadFile = new FileOutputStream("C:\\Users\\Hule-Elev\\Documents\\input.jar");
                    try {
                        if (metadata.getPathLower().equals("/text.jar")) {
                            client.files().downloadBuilder("/text.jar").download(downloadFile);
                            System.out.println("text.jar has been downloaded!");
                        }
                    } finally {
                        downloadFile.close();
                    }
                }
                catch (DbxException e)
                {
                    JOptionPane.showMessageDialog(null, "Unable to download file to local system\n Error: " + e);
                }
                catch (IOException e)
                {
                    JOptionPane.showMessageDialog(null, "Unable to download file to local system\n Error: " + e);
                }
            }

            if (!result.getHasMore()) {
                break;
            }

            result = client.files().listFolderContinue(result.getCursor());
        }
    }
}
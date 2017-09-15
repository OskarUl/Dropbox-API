import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.users.FullAccount;

import javax.swing.*;
import java.io.*;

public class main {
    private static final String ACCESS_TOKEN = "gJKkMgm0o8AAAAAAAAAAYSuQZFy2plTSmSAt02gsXVAxn2gTn-qVBaqIMvdZwy8q";

    public static void main(String args[]) throws DbxException {
        // Create Dropbox client
        DbxRequestConfig config = new DbxRequestConfig("dropbox", "en_US");
        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);

        // Get current account info
        FullAccount account = client.users().getCurrentAccount();
        System.out.println(account.getName().getDisplayName());

        // Get files and folder metadata from Dropbox root directory
        ListFolderResult result = client.files().listFolder("");
        while (true) {
            for (Metadata metadata : result.getEntries()) {
                System.out.println(metadata.getPathLower());
            }

            if (!result.getHasMore()) {
                break;
            }

            result = client.files().listFolderContinue(result.getCursor());
        }
        try
        {
            //output file for download --> storage location on local system to download file
            OutputStream downloadFile = new FileOutputStream("D:\\text");
            try
            {
                FileMetadata metadata = client.files().downloadBuilder("")
                        .download(downloadFile);
            }
            finally
            {
                downloadFile.close();
            }
        }
        //exception handled
        catch (DbxException e)
        {
            //error downloading file
            JOptionPane.showMessageDialog(null, "Unable to download file to local system\n Error: " + e);
        }
        catch (IOException e)
        {
            //error downloading file
            JOptionPane.showMessageDialog(null, "Unable to download file to local system\n Error: " + e);
        }
        // Upload "test.txt" to Dropbox
        /*try (InputStream in = new FileInputStream("C:\\Users\\Hule-Elev\\IdeaProjects\\serverUpdateTest\\Text.txt.txt")) {
            FileMetadata metadata = client.files().uploadBuilder("/Text.txt")
                    .uploadAndFinish(in);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
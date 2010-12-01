/**
 * Copyright (c) 2005, David A. Czarnecki
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 *      this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice,
 *      this list of conditions and the following disclaimer in the
 *      documentation and/or other materials provided with the distribution.
 *
 * Neither the name of the "David A. Czarnecki" and "TextAmerica4J" nor the names of
 * its contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Products derived from this software may not be called "TextAmerica4J",
 * nor may "TextAmerica4J" appear in their name, without prior written permission of
 * David A. Czarnecki.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND
 * CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO
 * EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.textamerica;

import org.apache.xmlrpc.Base64;
import org.apache.xmlrpc.XmlRpcClient;

import java.io.*;
import java.util.Vector;

/**
 * Java API for interacting with TextAmerica moblog service. You may find more information
 * on the TextAmerica API at <a href="http://www.textamerica.com/api.aspx">http://www.textamerica.com/api.aspx</a>.
 * <p/>
 * Usage:
 * <p/>
 *
 * @author David Czarnecki
 * @version $Id: TextAmerica4J.java,v 1.2 2005/04/20 14:55:02 czarneckid Exp $
 * @see <a href="http://www.textamerica.com/api.aspx">http://www.textamerica.com/api.aspx</a>
 */
public class TextAmerica4J {

    private static final String API_ENDPOINT = "http://xml.api.textamerica.com";

    private static final String TA_MOBLOG_GETMYMOBLOGS = "ta.Moblog.GetMyMoblogs";
    private static final String TA_MOBLOG_GETCOMMUNITYMOBLOGS = "ta.Moblog.GetCommunityMoblogs";
    private static final String TA_MOBLOG_UPDATE = "ta.Moblog.Update";
    private static final String TA_MOBLOG_CHANGETITLE = "ta.Moblog.ChangeTitle";
    private static final String TA_MOBLOG_CHANGETEXT = "ta.Moblog.ChangeText";
    private static final String TA_MOBLOG_CHANGEDOMAIN = "ta.Moblog.ChangeDomain";
    private static final String TA_MOBLOG_CHANGESECRETWORD = "ta.Moblog.ChangeSecretWord";

    private static final String TA_ENTRY_UPDATE = "ta.Entry.Update";
    private static final String TA_ENTRY_DELETE = "ta.Entry.Delete";

    private static final String TA_KEYWORDS_ADD = "ta.Keywords.Add";

    private static final String TA_FAVORITES_UPDATE = "ta.Favorites.Update";
    private static final String TA_FAVORITES_DELETE = "ta.Favorites.Delete";
    private static final String TA_FAVORITES_ASSIGN = "ta.Favorites.Assign";
    private static final String TA_FAVORITES_ADDMOBLOG = "ta.Favorites.AddMoblog";
    private static final String TA_FAVORITES_REMOVEMOBLOG = "ta.Favorites.RemoveMoblog";

    private static final String TA_BOOKMARKS_UPDATE = "ta.Bookmarks.Update";
    private static final String TA_BOOKMARKS_ASSIGN = "ta.Bookmarks.Assign";
    private static final String TA_BOOKMARKS_UNASSIGN = "ta.Bookmarks.UnAssign";
    private static final String TA_BOOKMARKS_DELETE = "ta.Bookmarks.Delete";
    private static final String TA_BOOKMARKS_UPDATEURL = "ta.Bookmarks.UpdateURL";
    private static final String TA_BOOKMARKS_REMOVEURL = "ta.Bookmarks.RemoveURL";

    private static final String TA_TEMPLATE_UPDATESECTION = "ta.Template.UpdateSection";
    private static final String TA_TEMPLATE_SETTEMPLATE = "ta.Template.SetTemplate";

    private String apiKey;
    private String login;
    private String password;
    private XmlRpcClient xmlRpcClient;

    /**
     * Create an instance to interact with TextAmerica moblog service
     *
     * @param apiKey   API key
     * @param login    Login ID
     * @param password Password
     * @throws Exception If there is an exception creating an XML-RPC client
     */
    public TextAmerica4J(String apiKey, String login, String password) throws Exception {
        this.apiKey = apiKey;
        this.login = login;
        this.password = password;
        xmlRpcClient = new XmlRpcClient(API_ENDPOINT);
    }

    /**
     * Helper method to check if an item is null or blank
     *
     * @param item Item
     * @return <code>true</code> if item is <code>null</code> or blank
     */
    private boolean checkNullOrBlank(String item) {
        return (item == null || "".equals(item));
    }

    /**
     * Prepare a vector with the default request parameters, apikey, userID, and password
     *
     * @return Cector with the default request parameters, apikey, userID, and password (added in that order)
     */
    protected Vector prepareDefaultRequestParameters() {
        Vector defaultParameters = new Vector();

        defaultParameters.add(apiKey);
        defaultParameters.add(login);
        defaultParameters.add(password);

        return defaultParameters;
    }

    /**
     * Returns a Vector of Hashtables where each hashtable contains the following keys, "title", "MoblogID", and "url"
     *
     * @return Vector of Hashtables where each hashtable contains the following keys, "title", "MoblogID", and "url"
     * @throws Exception If there is an error
     * @see <a href="http://www.textamerica.com/apicalls.aspx?call=Moblog.GetMyMoblogs">http://www.textamerica.com/apicalls.aspx?call=Moblog.GetMyMoblogs</a>
     */
    public Vector getMyMoblogs() throws Exception {
        Vector parameters = prepareDefaultRequestParameters();

        Object moblogs = xmlRpcClient.execute(TA_MOBLOG_GETMYMOBLOGS, parameters);

        return (Vector) moblogs;
    }

    /**
     * Returns a Vector of Hashtables where each hashtable contains the following keys, "title", "MoblogID", "url", and "postTo"
     * <p/>
     * Additional Parameters
     * <p/>
     * Which (Variable) - If variable is left blank, the method returns most recent 30 moblogs. If variable is numeric, the method returns the most recent number Variable. If variable is a date value, the method returns all moblogs created on or after Variable. If variable = "all", the method returns all community moblogs (large return).
     *
     * @param which If variable is left blank, the method returns most recent 30 moblogs. If variable is numeric, the method returns the most recent number Variable. If variable is a date value, the method returns all moblogs created on or after Variable. If variable = "all", the method returns all community moblogs (large return).
     * @return Vector of Hashtables where each hashtable contains the following keys, "title", "MoblogID", "url", and "postTo"
     * @throws Exception If there is an error
     * @see <a href="http://www.textamerica.com/apicalls.aspx?call=Moblog.GetCommunityMoblogs">http://www.textamerica.com/apicalls.aspx?call=Moblog.GetCommunityMoblogs</a>
     */
    public Vector getCommunityMoblogs(String which) throws Exception {
        Vector parameters = prepareDefaultRequestParameters();

        if (!checkNullOrBlank(which)) {
            parameters.add(which);
        }

        Object communityMoblogs = xmlRpcClient.execute(TA_MOBLOG_GETCOMMUNITYMOBLOGS, parameters);

        return (Vector) communityMoblogs;
    }

    /**
     * Changes the specific properties of your moblog such as title, text, domain, secret word. Returns a MoblogID.
     * <p/>
     * Additional Parameters
     * <p/>
     * MoblogID (Number) - ID associated with your moblog<br/>
     * Domain (Alphanumeric) - Domain name of your moblog<br/>
     * Secretword (Alphanumeric) - Secretword that makes up your "secret email address"<br/>
     * Title (String) - Title of your moblog<br/>
     * Text (String) - Description that appears on your moblog<br/>
     * ApprovalType (Character) - [C]ommunity where images must be approved by you - [P]rivate (default) where images post immediately<br/>
     * AllowComments (Character) - [Y]es (default) or [N]o<br/>
     *
     * @param moblogID      ID associated with your moblog
     * @param domain        Domain name of your moblog
     * @param secretWord    Secretword that makes up your "secret email address"
     * @param title         Title of your moblog
     * @param description   Description that appears on your moblog
     * @param approvalType  [C]ommunity where images must be approved by you - [P]rivate (default) where images post immediately
     * @param allowComments [Y]es (default) or [N]o
     * @return Moblog ID
     * @throws Exception If there is an error
     * @see <a href="http://www.textamerica.com/apicalls.aspx?call=Moblog.Update">http://www.textamerica.com/apicalls.aspx?call=Moblog.Update</a>
     */
    public String update(Integer moblogID, String domain, String secretWord, String title, String description,
                         String approvalType, String allowComments) throws Exception {
        Vector parameters = prepareDefaultRequestParameters();

        parameters.add(moblogID);
        parameters.add(domain);
        parameters.add(secretWord);
        parameters.add(title);
        parameters.add(description);
        parameters.add(approvalType);
        parameters.add(allowComments);

        Object returnedMoblogID = xmlRpcClient.execute(TA_MOBLOG_UPDATE, parameters);

        return (String) returnedMoblogID;
    }

    /**
     * Changes the specific properties of your moblog such as title, text, domain, secret word. Returns a MoblogID.
     * <p/>
     * Additional Parameters
     * <p/>
     * MoblogID (Number) - ID associated with your moblog<br/>
     * Domain (Alphanumeric) - Domain name of your moblog<br/>
     * Secretword (Alphanumeric) - Secretword that makes up your "secret email address"<br/>
     * Title (String) - Title of your moblog<br/>
     * Text (String) - Description that appears on your moblog<br/>
     * ApprovalType (Character) - [C]ommunity where images must be approved by you - [P]rivate (default) where images post immediately<br/>
     * AllowComments (Character) - [Y]es (default) or [N]o<br/>
     *
     * @param moblogID      ID associated with your moblog
     * @param domain        Domain name of your moblog
     * @param secretWord    Secretword that makes up your "secret email address"
     * @param title         Title of your moblog
     * @param description   Description that appears on your moblog
     * @param approvalType  [C]ommunity where images must be approved by you - [P]rivate (default) where images post immediately
     * @param allowComments [Y]es (default) or [N]o
     * @return Moblog ID
     * @throws Exception If there is an error
     * @see <a href="http://www.textamerica.com/apicalls.aspx?call=Moblog.Update">http://www.textamerica.com/apicalls.aspx?call=Moblog.Update</a>
     */
    public String update(int moblogID, String domain, String secretWord, String title, String description,
                         char approvalType, char allowComments) throws Exception {
        return update(new Integer(moblogID), domain, secretWord, title, description, Character.toString(approvalType), Character.toString(allowComments));
    }

    /**
     * Updates the title of a moblog. Returns the MoblogID.
     * <p/>
     * Additional Parameters
     * <p/>
     * MoblogID (Number) - ID associated with your moblog<br/>
     * Title (String) - New title that appears on your moblog<br/>
     *
     * @param moblogID ID associated with your moblog
     * @param title    New title that appears on your moblog
     * @return Moblog ID
     * @throws Exception If there is an error
     * @see <a href="http://www.textamerica.com/apicalls.aspx?call=Moblog.ChangeTitle">http://www.textamerica.com/apicalls.aspx?call=Moblog.ChangeTitle</a>
     */
    public String changeTitle(Integer moblogID, String title) throws Exception {
        Vector parameters = prepareDefaultRequestParameters();

        parameters.add(moblogID);
        parameters.add(title);

        Object returnedMoblogID = xmlRpcClient.execute(TA_MOBLOG_CHANGETITLE, parameters);

        return (String) returnedMoblogID;
    }

    /**
     * Updates the title of a moblog. Returns the MoblogID.
     * <p/>
     * Additional Parameters
     * <p/>
     * MoblogID (Number) - ID associated with your moblog<br/>
     * Title (String) - New title that appears on your moblog<br/>
     *
     * @param moblogID ID associated with your moblog
     * @param title    New title that appears on your moblog
     * @return Moblog ID
     * @throws Exception If there is an error
     * @see <a href="http://www.textamerica.com/apicalls.aspx?call=Moblog.ChangeTitle">http://www.textamerica.com/apicalls.aspx?call=Moblog.ChangeTitle</a>
     */
    public String changeTitle(int moblogID, String title) throws Exception {
        return changeTitle(new Integer(moblogID), title);
    }

    /**
     * Updates the description of a moblog. Returns the MoblogID.
     * <p/>
     * Additional Parameters
     * <p/>
     * MoblogID (Number) - ID associated with your moblog<br/>
     * Text (String) - ID associated with your moblog<br/>
     *
     * @param moblogID ID associated with your moblog
     * @param text     ID associated with your moblog
     * @return Moblog ID
     * @throws Exception If there is an error
     * @see <a href="http://www.textamerica.com/apicalls.aspx?call=Moblog.ChangeText">http://www.textamerica.com/apicalls.aspx?call=Moblog.ChangeText</a>
     */
    public String changeText(Integer moblogID, String text) throws Exception {
        Vector parameters = prepareDefaultRequestParameters();

        parameters.add(moblogID);
        parameters.add(text);

        Object returnedMoblogID = xmlRpcClient.execute(TA_MOBLOG_CHANGETEXT, parameters);

        return (String) returnedMoblogID;
    }

    /**
     * Updates the description of a moblog. Returns the MoblogID.
     * <p/>
     * Additional Parameters
     * <p/>
     * MoblogID (Number) - ID associated with your moblog<br/>
     * Text (String) - ID associated with your moblog<br/>
     *
     * @param moblogID ID associated with your moblog
     * @param text     ID associated with your moblog
     * @return Moblog ID
     * @throws Exception If there is an error
     * @see <a href="http://www.textamerica.com/apicalls.aspx?call=Moblog.ChangeText">http://www.textamerica.com/apicalls.aspx?call=Moblog.ChangeText</a>
     */
    public String changeText(int moblogID, String text) throws Exception {
        return changeText(new Integer(moblogID), text);
    }

    /**
     * Changes an existing moblog domain. Returns the MoblogID.
     * <p/>
     * Additional Parameters
     * <p/>
     * MoblogID (Number) - ID associated with your moblog<br/>
     * NewDomain (Alphanumeric) - New domain name of your moblog<br/>
     *
     * @param moblogID ID associated with your moblog
     * @param domain   New domain name of your moblog
     * @return Moblog ID
     * @throws Exception If there is an error
     * @see <a href="http://www.textamerica.com/apicalls.aspx?call=Moblog.ChangeDomain">http://www.textamerica.com/apicalls.aspx?call=Moblog.ChangeDomain</a>
     */
    public String changeDomain(Integer moblogID, String domain) throws Exception {
        Vector parameters = prepareDefaultRequestParameters();

        parameters.add(moblogID);
        parameters.add(domain);

        Object returnedMoblogID = xmlRpcClient.execute(TA_MOBLOG_CHANGEDOMAIN, parameters);

        return (String) returnedMoblogID;
    }

    /**
     * Changes an existing moblog domain. Returns the MoblogID.
     * <p/>
     * Additional Parameters
     * <p/>
     * MoblogID (Number) - ID associated with your moblog<br/>
     * NewDomain (Alphanumeric) - New domain name of your moblog<br/>
     *
     * @param moblogID ID associated with your moblog
     * @param domain   New domain name of your moblog
     * @return Moblog ID
     * @throws Exception If there is an error
     * @see <a href="http://www.textamerica.com/apicalls.aspx?call=Moblog.ChangeDomain">http://www.textamerica.com/apicalls.aspx?call=Moblog.ChangeDomain</a>
     */
    public String changeDomain(int moblogID, String domain) throws Exception {
        return changeDomain(new Integer(moblogID), domain);
    }

    /**
     * Changes the secret word of a moblog. Returns the MoblogID.
     * <p/>
     * Additional Parameters
     * <p/>
     * MoblogID (Number) - ID associated with your moblog<br/>
     * NewSecretWord (Alphanumeric) - New secret word of your moblog<br/>
     *
     * @param moblogID   ID associated with your moblog
     * @param secretWord New secret word of your moblog
     * @return Moblog ID
     * @throws Exception If there is an error
     * @see <a href="http://www.textamerica.com/apicalls.aspx?call=Moblog.ChangeSecretWord">http://www.textamerica.com/apicalls.aspx?call=Moblog.ChangeSecretWord</a>
     */
    public String changeSecretWord(Integer moblogID, String secretWord) throws Exception {
        Vector parameters = prepareDefaultRequestParameters();

        parameters.add(moblogID);
        parameters.add(secretWord);

        Object returnedMoblogID = xmlRpcClient.execute(TA_MOBLOG_CHANGESECRETWORD, parameters);

        return (String) returnedMoblogID;
    }

    /**
     * Changes the secret word of a moblog. Returns the MoblogID.
     * <p/>
     * Additional Parameters
     * <p/>
     * MoblogID (Number) - ID associated with your moblog<br/>
     * NewSecretWord (Alphanumeric) - New secret word of your moblog<br/>
     *
     * @param moblogID   ID associated with your moblog
     * @param secretWord New secret word of your moblog
     * @return Moblog ID
     * @throws Exception If there is an error
     * @see <a href="http://www.textamerica.com/apicalls.aspx?call=Moblog.ChangeSecretWord">http://www.textamerica.com/apicalls.aspx?call=Moblog.ChangeSecretWord</a>
     */
    public String changeSecretWord(int moblogID, String secretWord) throws Exception {
        return changeSecretWord(new Integer(moblogID), secretWord);
    }

    /**
     * Updates or creates a new image entry. Returns the EntryID.
     * <p/>
     * Additional Parameters
     * <p/>
     * MoblogID (Number) - ID associated with your moblog <br/>
     * EntryID (Number) - ID associated with the entry you are updating. Use 0 to create a new entry.<br/>
     * Title (String) - Title associated with the entry<br/>
     * Text (String) - Description associated with this entry<br/>
     * CategoryID (Number) - Category id to associate with this entry (Default is 0).<br/>
     * ImageData (Base64EncodedString) - Image or movie file data encoded in base 64 format<br/>
     * FileType (Alphanumeric) - Allowed values are: "JPG", "JPEG", "MP4", "3GP", "3G2", "MOV"<br/>
     *
     * @param moblogID   ID associated with your moblog
     * @param entryID    ID associated with the entry you are updating. Use 0 to create a new entry.
     * @param title      Title associated with the entry
     * @param text       Description associated with this entry
     * @param categoryID Category id to associate with this entry (Default is 0).
     * @param imageData  Image or movie file data encoded in base 64 format
     * @param fileType   Allowed values are: "JPG", "JPEG", "MP4", "3GP", "3G2", "MOV"
     * @return Entry ID
     * @throws Exception If there is an error
     * @see <a href="http://www.textamerica.com/apicalls.aspx?call=Entry.Update">http://www.textamerica.com/apicalls.aspx?call=Entry.Update</a>
     */
    public String entryUpdate(Integer moblogID, Integer entryID, String title, String text,
                              Integer categoryID, File imageData, String fileType) throws Exception {
        Vector parameters = prepareDefaultRequestParameters();

        parameters.add(moblogID);
        parameters.add(entryID);
        parameters.add(title);
        parameters.add(text);
        parameters.add(categoryID);

        InputStream is = new FileInputStream(imageData);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(8192);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(is);
        int bytesRead = 0;
        byte[] temp = new byte[8192];
        while (-1 != (bytesRead = bufferedInputStream.read(temp))) {
            byteArrayOutputStream.write(temp, 0, bytesRead);
        }

        is.close();
        bufferedInputStream.close();

        byte[] encodedBytes = Base64.encode(byteArrayOutputStream.toByteArray());

        byteArrayOutputStream.close();

        parameters.add(new String(encodedBytes));
        parameters.add(fileType);

        Object response = xmlRpcClient.execute(TA_ENTRY_UPDATE, parameters);

        return (String) response;
    }

    /**
     * Updates or creates a new image entry. Returns the EntryID.
     * <p/>
     * Additional Parameters
     * <p/>
     * MoblogID (Number) - ID associated with your moblog <br/>
     * EntryID (Number) - ID associated with the entry you are updating. Use 0 to create a new entry.<br/>
     * Title (String) - Title associated with the entry<br/>
     * Text (String) - Description associated with this entry<br/>
     * CategoryID (Number) - Category id to associate with this entry (Default is 0).<br/>
     * ImageData (Base64EncodedString) - Image or movie file data encoded in base 64 format<br/>
     * FileType (Alphanumeric) - Allowed values are: "JPG", "JPEG", "MP4", "3GP", "3G2", "MOV"<br/>
     *
     * @param moblogID   ID associated with your moblog
     * @param entryID    ID associated with the entry you are updating. Use 0 to create a new entry.
     * @param title      Title associated with the entry
     * @param text       Description associated with this entry
     * @param categoryID Category id to associate with this entry (Default is 0).
     * @param imageData  Image or movie file data encoded in base 64 format
     * @param fileType   Allowed values are: "JPG", "JPEG", "MP4", "3GP", "3G2", "MOV"
     * @return Entry ID
     * @throws Exception If there is an error
     * @see <a href="http://www.textamerica.com/apicalls.aspx?call=Entry.Update">http://www.textamerica.com/apicalls.aspx?call=Entry.Update</a>
     */
    public String entryUpdate(int moblogID, int entryID, String title, String text,
                              int categoryID, File imageData, String fileType) throws Exception {
        return entryUpdate(new Integer(moblogID), new Integer(entryID), title, text, new Integer(categoryID), imageData, fileType);
    }

    /**
     * Deletes a specified entry from a moblog. Returns "OK".
     * <p/>
     * Additional Parameters
     * <p/>
     * MoblogID (Number) - ID associated with your moblog<br/>
     * EntryID (Number) - ID associated with the entry you are deleting<br/>
     *
     * @param moblogID ID associated with your moblog
     * @param entryID  ID associated with the entry you are deleting
     * @return "OK"
     * @throws Exception If there is an error
     * @see <a href="http://www.textamerica.com/apicalls.aspx?call=Entry.Delete">http://www.textamerica.com/apicalls.aspx?call=Entry.Delete</a>
     */
    public String entryDelete(Integer moblogID, Integer entryID) throws Exception {
        Vector parameters = prepareDefaultRequestParameters();

        parameters.add(moblogID);
        parameters.add(entryID);

        Object response = xmlRpcClient.execute(TA_ENTRY_DELETE, parameters);

        return (String) response;
    }

    /**
     * Deletes a specified entry from a moblog. Returns "OK".
     * <p/>
     * Additional Parameters
     * <p/>
     * MoblogID (Number) - ID associated with your moblog<br/>
     * EntryID (Number) - ID associated with the entry you are deleting<br/>
     *
     * @param moblogID ID associated with your moblog
     * @param entryID  ID associated with the entry you are deleting
     * @return "OK"
     * @throws Exception If there is an error
     * @see <a href="http://www.textamerica.com/apicalls.aspx?call=Entry.Delete">http://www.textamerica.com/apicalls.aspx?call=Entry.Delete</a>
     */
    public String entryDelete(int moblogID, int entryID) throws Exception {
        return entryDelete(new Integer(moblogID), new Integer(entryID));
    }

    /**
     * Adds keywords to a specified entry / image
     * <p/>
     * Additional Parameters
     * <p/>
     * EntryID (Number) - ID associated with the image that keywords are being added to <br/>
     * Keywords (String) - Keywords separated by commas <br/>
     *
     * @param entryID  ID associated with the image that keywords are being added to
     * @param keywords Keywords separated by commas
     * @return "OK"
     * @throws Exception If there is an error
     * @see <a href="http://www.textamerica.com/apicalls.aspx?call=Keywords.Add">http://www.textamerica.com/apicalls.aspx?call=Keywords.Add</a>
     */
    public String addKeywords(Integer entryID, String keywords) throws Exception {
        Vector parameters = prepareDefaultRequestParameters();

        parameters.add(entryID);
        parameters.add(keywords);

        Object response = xmlRpcClient.execute(TA_KEYWORDS_ADD, parameters);

        return (String) response;
    }

    /**
     * Adds keywords to a specified entry / image
     * <p/>
     * Additional Parameters
     * <p/>
     * EntryID (Number) - ID associated with the image that keywords are being added to <br/>
     * Keywords (String) - Keywords separated by commas <br/>
     *
     * @param entryID  ID associated with the image that keywords are being added to
     * @param keywords Keywords separated by commas
     * @return "OK"
     * @throws Exception If there is an error
     * @see <a href="http://www.textamerica.com/apicalls.aspx?call=Keywords.Add">http://www.textamerica.com/apicalls.aspx?call=Keywords.Add</a>
     */
    public String addKeywords(int entryID, String keywords) throws Exception {
        return addKeywords(new Integer(entryID), keywords);
    }

    /**
     * TBD
     * <p/>
     * Additional Parameters
     * <p/>
     * ListID (Number) - ID associated with this list. Use 0 to create a new list.<br/>
     * ListTitle (Number) - Title associated with the list you are updating/creating.<br/>
     *
     * @param listID ID associated with this list. Use 0 to create a new list.
     * @param title  Title associated with the list you are updating/creating.
     * @return
     * @throws Exception If there is an error
     * @see <a href="http://www.textamerica.com/apicalls.aspx?call=Favorites.Update">http://www.textamerica.com/apicalls.aspx?call=Favorites.Update</a>
     */
    public Object favoritesUpdate(Integer listID, String title) throws Exception {
        Vector parameters = prepareDefaultRequestParameters();

        parameters.add(listID);
        parameters.add(title);

        Object response = xmlRpcClient.execute(TA_FAVORITES_UPDATE, parameters);

        return response;
    }

    /**
     * TBD
     * <p/>
     * Additional Parameters
     * <p/>
     * ListID (Number) - ID associated with this list. Use 0 to create a new list.<br/>
     * ListTitle (Number) - Title associated with the list you are updating/creating.<br/>
     *
     * @param listID ID associated with this list. Use 0 to create a new list.
     * @param title  Title associated with the list you are updating/creating.
     * @return
     * @throws Exception If there is an error
     * @see <a href="http://www.textamerica.com/apicalls.aspx?call=Favorites.Update">http://www.textamerica.com/apicalls.aspx?call=Favorites.Update</a>
     */
    public Object favoritesUpdate(int listID, String title) throws Exception {
        return favoritesUpdate(new Integer(listID), title);
    }

    /**
     * Remove a moblog to a Favorites List.
     * <p/>
     * Additional Parameters
     * <p/>
     * ListID (Number) - ID associated with this list. Use 0 to assign "My Favorites".<br/>
     * MoblogURL (String) - URL of the moblog that you are removing from this Favorites List<br/>
     *
     * @param listID    ID associated with this list. Use 0 to assign "My Favorites".
     * @param moblogURL URL of the moblog that you are removing from this Favorites List
     * @return
     * @throws Exception If there is an error
     * @see <a href="http://www.textamerica.com/apicalls.aspx?call=Favorites.Delete">http://www.textamerica.com/apicalls.aspx?call=Favorites.Delete</a>
     */
    public Object favoritesDelete(Integer listID, String moblogURL) throws Exception {
        Vector parameters = prepareDefaultRequestParameters();

        parameters.add(listID);
        parameters.add(moblogURL);

        Object response = xmlRpcClient.execute(TA_FAVORITES_DELETE, parameters);

        return response;
    }

    /**
     * Remove a moblog to a Favorites List.
     * <p/>
     * Additional Parameters
     * <p/>
     * ListID (Number) - ID associated with this list. Use 0 to assign "My Favorites".<br/>
     * MoblogURL (String) - URL of the moblog that you are removing from this Favorites List<br/>
     *
     * @param listID    ID associated with this list. Use 0 to assign "My Favorites".
     * @param moblogURL URL of the moblog that you are removing from this Favorites List
     * @return
     * @throws Exception If there is an error
     * @see <a href="http://www.textamerica.com/apicalls.aspx?call=Favorites.Delete">http://www.textamerica.com/apicalls.aspx?call=Favorites.Delete</a>
     */
    public Object favoritesDelete(int listID, String moblogURL) throws Exception {
        return favoritesDelete(new Integer(listID), moblogURL);
    }

    /**
     * Assigns a Favorites List to be displayed on a moblog.
     * <p/>
     * Additional Parameters
     * <p/>
     * MoblogID (Number) - ID of the moblog that will display this list<br/>
     * ListID (Number) - ID associated with this list. Use 0 to assign "My Favorites".<br/>
     *
     * @param moblogID ID of the moblog that will display this list
     * @param listID   ID associated with this list. Use 0 to assign "My Favorites".
     * @return
     * @throws Exception If there is an error
     * @see <a href="http://www.textamerica.com/apicalls.aspx?call=Favorites.Assign">http://www.textamerica.com/apicalls.aspx?call=Favorites.Assign</a>
     */
    public Object favoritesAssign(Integer moblogID, Integer listID) throws Exception {
        Vector parameters = prepareDefaultRequestParameters();

        parameters.add(moblogID);
        parameters.add(listID);

        Object response = xmlRpcClient.execute(TA_FAVORITES_ASSIGN, parameters);

        return response;
    }

    /**
     * Assigns a Favorites List to be displayed on a moblog.
     * <p/>
     * Additional Parameters
     * <p/>
     * MoblogID (Number) - ID of the moblog that will display this list<br/>
     * ListID (Number) - ID associated with this list. Use 0 to assign "My Favorites".<br/>
     *
     * @param moblogID ID of the moblog that will display this list
     * @param listID   ID associated with this list. Use 0 to assign "My Favorites".
     * @return
     * @throws Exception If there is an error
     * @see <a href="http://www.textamerica.com/apicalls.aspx?call=Favorites.Assign">http://www.textamerica.com/apicalls.aspx?call=Favorites.Assign</a>
     */
    public Object favoritesAssign(int moblogID, int listID) throws Exception {
        return favoritesAssign(new Integer(moblogID), new Integer(listID));
    }

    /**
     * Add a moblog to a Favorites List.
     * <p/>
     * Additional Parameters
     * <p/>
     * ListID (Number) - ID associated with this list. Use 0 to assign "My Favorites".<br/>
     * MoblogURL (String) - URL of the moblog that you will add to this Favorites List<br/>
     *
     * @param listID    ID associated with this list. Use 0 to assign "My Favorites".
     * @param moblogURL URL of the moblog that you will add to this Favorites List
     * @return
     * @throws Exception If there is an error
     * @see <a href="http://www.textamerica.com/apicalls.aspx?call=Favorites.AddMoblog">http://www.textamerica.com/apicalls.aspx?call=Favorites.AddMoblog</a>
     */
    public Object favoritesAddMoblog(Integer listID, String moblogURL) throws Exception {
        Vector parameters = prepareDefaultRequestParameters();

        parameters.add(listID);
        parameters.add(moblogURL);

        Object response = xmlRpcClient.execute(TA_FAVORITES_ADDMOBLOG, parameters);

        return response;
    }

    /**
     * Add a moblog to a Favorites List.
     * <p/>
     * Additional Parameters
     * <p/>
     * ListID (Number) - ID associated with this list. Use 0 to assign "My Favorites".<br/>
     * MoblogURL (String) - URL of the moblog that you will add to this Favorites List<br/>
     *
     * @param listID    ID associated with this list. Use 0 to assign "My Favorites".
     * @param moblogURL URL of the moblog that you will add to this Favorites List
     * @return
     * @throws Exception If there is an error
     * @see <a href="http://www.textamerica.com/apicalls.aspx?call=Favorites.AddMoblog">http://www.textamerica.com/apicalls.aspx?call=Favorites.AddMoblog</a>
     */
    public Object favoritesAddMoblog(int listID, String moblogURL) throws Exception {
        return favoritesAddMoblog(new Integer(listID), moblogURL);
    }

    /**
     * Remove a moblog to a Favorites List.
     * <p/>
     * Additional Parameters
     * <p/>
     * ListID (Number) - ID associated with this list. Use 0 to assign "My Favorites".<br/>
     * MoblogURL (String) - URL of the moblog that you are removing from this Favorites List<br/>
     *
     * @param listID    ID associated with this list. Use 0 to assign "My Favorites".
     * @param moblogURL URL of the moblog that you are removing from this Favorites List
     * @return
     * @throws Exception If there is an error
     * @see <a href="http://www.textamerica.com/apicalls.aspx?call=Favorites.RemoveMoblog">http://www.textamerica.com/apicalls.aspx?call=Favorites.RemoveMoblog</a>
     */
    public Object favoritesRemoveMoblog(Integer listID, String moblogURL) throws Exception {
        Vector parameters = prepareDefaultRequestParameters();

        parameters.add(listID);
        parameters.add(moblogURL);

        Object response = xmlRpcClient.execute(TA_FAVORITES_REMOVEMOBLOG, parameters);

        return response;
    }

    /**
     * Remove a moblog to a Favorites List.
     * <p/>
     * Additional Parameters
     * <p/>
     * ListID (Number) - ID associated with this list. Use 0 to assign "My Favorites".<br/>
     * MoblogURL (String) - URL of the moblog that you are removing from this Favorites List<br/>
     *
     * @param listID    ID associated with this list. Use 0 to assign "My Favorites".
     * @param moblogURL URL of the moblog that you are removing from this Favorites List
     * @return
     * @throws Exception If there is an error
     * @see <a href="http://www.textamerica.com/apicalls.aspx?call=Favorites.RemoveMoblog">http://www.textamerica.com/apicalls.aspx?call=Favorites.RemoveMoblog</a>
     */
    public Object favoritesRemoveMoblog(int listID, String moblogURL) throws Exception {
        return favoritesRemoveMoblog(new Integer(listID), moblogURL);
    }

    /**
     * Updates or creates a new bookmark list. Returns the bookmark list id.
     * <p/>
     * Additional Parameters
     * <p/>
     * ListID (Number) - ID associated with the list you are updating. Use 0 to create a new list.<br/>
     * Title (String) - Title of the bookmark list<br/>
     *
     * @param listID ID associated with the list you are updating. Use 0 to create a new list.
     * @param title  Title of the bookmark list
     * @return Returns the bookmark list id.
     * @throws Exception If there is an error
     * @see <a href="http://www.textamerica.com/apicalls.aspx?call=Bookmarks.Update">http://www.textamerica.com/apicalls.aspx?call=Bookmarks.Update</a>
     */
    public String bookmarksUpdate(Integer listID, String title) throws Exception {
        Vector parameters = prepareDefaultRequestParameters();

        parameters.add(listID);
        parameters.add(title);

        Object response = xmlRpcClient.execute(TA_BOOKMARKS_UPDATE, parameters);

        return (String) response;
    }

    /**
     * Updates or creates a new bookmark list. Returns the bookmark list id.
     * <p/>
     * Additional Parameters
     * <p/>
     * ListID (Number) - ID associated with the list you are updating. Use 0 to create a new list.<br/>
     * Title (String) - Title of the bookmark list<br/>
     *
     * @param listID ID associated with the list you are updating. Use 0 to create a new list.
     * @param title  Title of the bookmark list
     * @return Returns the bookmark list id.
     * @throws Exception If there is an error
     * @see <a href="http://www.textamerica.com/apicalls.aspx?call=Bookmarks.Update">http://www.textamerica.com/apicalls.aspx?call=Bookmarks.Update</a>
     */
    public String bookmarksUpdate(int listID, String title) throws Exception {
        return bookmarksUpdate(new Integer(listID), title);
    }

    /**
     * Assigns a bookmark list to a specified moblog.
     * <p/>
     * Additional Parameters
     * <p/>
     * MoblogID (Number) - ID associated with your moblog that will display the list<br/>
     * ListID (Number) - ID associated with the bookmark list<br/>
     *
     * @param moblogID ID associated with your moblog that will display the list
     * @param listID   ID associated with the bookmark list
     * @return "OK"
     * @throws Exception If there is an error
     * @see <a href="http://www.textamerica.com/apicalls.aspx?call=Bookmarks.Assign">http://www.textamerica.com/apicalls.aspx?call=Bookmarks.Assign</a>
     */
    public String bookmarksAssign(Integer moblogID, Integer listID) throws Exception {
        Vector parameters = prepareDefaultRequestParameters();

        parameters.add(moblogID);
        parameters.add(listID);

        Object response = xmlRpcClient.execute(TA_BOOKMARKS_ASSIGN, parameters);

        return (String) response;
    }

    /**
     * Assigns a bookmark list to a specified moblog.
     * <p/>
     * Additional Parameters
     * <p/>
     * MoblogID (Number) - ID associated with your moblog that will display the list<br/>
     * ListID (Number) - ID associated with the bookmark list<br/>
     *
     * @param moblogID ID associated with your moblog that will display the list
     * @param listID   ID associated with the bookmark list
     * @return "OK"
     * @throws Exception If there is an error
     * @see <a href="http://www.textamerica.com/apicalls.aspx?call=Bookmarks.Assign">http://www.textamerica.com/apicalls.aspx?call=Bookmarks.Assign</a>
     */
    public String bookmarksAssign(int moblogID, int listID) throws Exception {
        return bookmarksAssign(new Integer(moblogID), new Integer(listID));
    }

    /**
     * Removes a bookmark list from a specified moblog
     * <p/>
     * Additional Parameters
     * <p/>
     * MoblogID (Number) - ID associated with your moblog that will display the list<br/>
     * ListID (Number) - ID associated with the bookmark list<br/>
     *
     * @param moblogID ID associated with your moblog that will display the list
     * @param listID   ID associated with the bookmark list
     * @return
     * @throws Exception If there is an error
     * @see <a href="http://www.textamerica.com/apicalls.aspx?call=Bookmarks.UnAssign">http://www.textamerica.com/apicalls.aspx?call=Bookmarks.UnAssign</a>
     */
    public String bookmarksUnAssign(Integer moblogID, Integer listID) throws Exception {
        Vector parameters = prepareDefaultRequestParameters();

        parameters.add(moblogID);
        parameters.add(listID);

        Object response = xmlRpcClient.execute(TA_BOOKMARKS_UNASSIGN, parameters);

        return (String) response;
    }

    /**
     * Removes a bookmark list from a specified moblog
     * <p/>
     * Additional Parameters
     * <p/>
     * MoblogID (Number) - ID associated with your moblog that will display the list<br/>
     * ListID (Number) - ID associated with the bookmark list<br/>
     *
     * @param moblogID ID associated with your moblog that will display the list
     * @param listID   ID associated with the bookmark list
     * @return
     * @throws Exception If there is an error
     * @see <a href="http://www.textamerica.com/apicalls.aspx?call=Bookmarks.UnAssign">http://www.textamerica.com/apicalls.aspx?call=Bookmarks.UnAssign</a>
     */
    public String bookmarksUnAssign(int moblogID, int listID) throws Exception {
        return bookmarksUnAssign(new Integer(moblogID), new Integer(listID));
    }

    /**
     * Deletes a bookmark list and all URLs on that list
     * <p/>
     * Additional Parameters
     * <p/>
     * ListID (Number) - ID associated with the bookmark list you are deleting<br/>
     *
     * @param listID ID associated with the bookmark list you are deleting
     * @return
     * @throws Exception If there is an error
     * @see <a href="http://www.textamerica.com/apicalls.aspx?call=Bookmarks.Delete">http://www.textamerica.com/apicalls.aspx?call=Bookmarks.Delete</a>
     */
    public String bookmarksDelete(Integer listID) throws Exception {
        Vector parameters = prepareDefaultRequestParameters();

        parameters.add(listID);

        Object response = xmlRpcClient.execute(TA_BOOKMARKS_DELETE, parameters);

        return (String) response;
    }

    /**
     * Deletes a bookmark list and all URLs on that list
     * <p/>
     * Additional Parameters
     * <p/>
     * ListID (Number) - ID associated with the bookmark list you are deleting<br/>
     *
     * @param listID ID associated with the bookmark list you are deleting
     * @return
     * @throws Exception If there is an error
     * @see <a href="http://www.textamerica.com/apicalls.aspx?call=Bookmarks.Delete">http://www.textamerica.com/apicalls.aspx?call=Bookmarks.Delete</a>
     */
    public String bookmarksDelete(int listID) throws Exception {
        return bookmarksDelete(new Integer(listID));
    }

    /**
     * Updates or creates a new URL on a specified list.
     * <p/>
     * Additional Parameters
     * <p/>
     * ListID (Number) - ID associated with your moblog<br/>
     * URL (String) - Full URL to add to the list<br/>
     *
     * @param listID ID associated with your moblog
     * @param url    Full URL to add to the list
     * @return
     * @throws Exception If there is an error
     * @see <a href="http://www.textamerica.com/apicalls.aspx?call=Bookmarks.UpdateURL">http://www.textamerica.com/apicalls.aspx?call=Bookmarks.UpdateURL</a>
     */
    public String bookmarksUpdateURL(Integer listID, String url) throws Exception {
        Vector parameters = prepareDefaultRequestParameters();

        parameters.add(listID);
        parameters.add(url);

        Object response = xmlRpcClient.execute(TA_BOOKMARKS_UPDATEURL, parameters);

        return (String) response;
    }

    /**
     * Updates or creates a new URL on a specified list.
     * <p/>
     * Additional Parameters
     * <p/>
     * ListID (Number) - ID associated with your moblog<br/>
     * URL (String) - Full URL to add to the list<br/>
     *
     * @param listID ID associated with your moblog
     * @param url    Full URL to add to the list
     * @return
     * @throws Exception If there is an error
     * @see <a href="http://www.textamerica.com/apicalls.aspx?call=Bookmarks.UpdateURL">http://www.textamerica.com/apicalls.aspx?call=Bookmarks.UpdateURL</a>
     */
    public String bookmarksUpdateURL(int listID, String url) throws Exception {
        return bookmarksUpdateURL(new Integer(listID), url);
    }

    /**
     * Removes a URL from a specified bookmark list.
     * <p/>
     * Additional Parameters
     * <p/>
     * ListID (Number) - ID associated with the bookmark list <br/>
     * URL (String) - URL that will be removed <br/>
     *
     * @param listID ID associated with the bookmark list
     * @param url    URL that will be removed
     * @return
     * @throws Exception If there is an error
     * @see <a href="http://www.textamerica.com/apicalls.aspx?call=Bookmarks.RemoveURL">http://www.textamerica.com/apicalls.aspx?call=Bookmarks.RemoveURL</a>
     */
    public String bookmarksRemoveURL(Integer listID, String url) throws Exception {
        Vector parameters = prepareDefaultRequestParameters();

        parameters.add(listID);
        parameters.add(url);

        Object response = xmlRpcClient.execute(TA_BOOKMARKS_REMOVEURL, parameters);

        return (String) response;
    }

    /**
     * Removes a URL from a specified bookmark list.
     * <p/>
     * Additional Parameters
     * <p/>
     * ListID (Number) - ID associated with the bookmark list <br/>
     * URL (String) - URL that will be removed <br/>
     *
     * @param listID ID associated with the bookmark list
     * @param url    URL that will be removed
     * @return
     * @throws Exception If there is an error
     * @see <a href="http://www.textamerica.com/apicalls.aspx?call=Bookmarks.RemoveURL">http://www.textamerica.com/apicalls.aspx?call=Bookmarks.RemoveURL</a>
     */
    public String bookmarksRemoveURL(int listID, String url) throws Exception {
        return bookmarksRemoveURL(new Integer(listID), url);
    }

    /**
     * Updates the section HTML for a specified moblog
     * <p/>
     * Additional Parameters
     * <p/>
     * MoblogID (Number) - ID associated with your moblog that will update<br/>
     * SectionID (String) - Allowed values : "header", "footer", "frontpage", "details", "entry", "comment", "result"<br/>
     * HTMLCode (String) - HTML code that will display<br/>
     *
     * @param moblogID  ID associated with your moblog that will update
     * @param sectionID Allowed values : "header", "footer", "frontpage", "details", "entry", "comment", "result"
     * @param htmlCode  HTML code that will display
     * @return
     * @throws Exception If there is an error
     * @see <a href="http://www.textamerica.com/apicalls.aspx?call=Template.UpdateSection">http://www.textamerica.com/apicalls.aspx?call=Template.UpdateSection</a>
     */
    public String templateUpdateSection(Integer moblogID, String sectionID, String htmlCode) throws Exception {
        Vector parameters = prepareDefaultRequestParameters();

        parameters.add(moblogID);
        parameters.add(sectionID);
        parameters.add(htmlCode);

        Object response = xmlRpcClient.execute(TA_TEMPLATE_UPDATESECTION, parameters);

        return (String) response;
    }

    /**
     * Updates the section HTML for a specified moblog
     * <p/>
     * Additional Parameters
     * <p/>
     * MoblogID (Number) - ID associated with your moblog that will update<br/>
     * SectionID (String) - Allowed values : "header", "footer", "frontpage", "details", "entry", "comment", "result"<br/>
     * HTMLCode (String) - HTML code that will display<br/>
     *
     * @param moblogID  ID associated with your moblog that will update
     * @param sectionID Allowed values : "header", "footer", "frontpage", "details", "entry", "comment", "result"
     * @param htmlCode  HTML code that will display
     * @return
     * @throws Exception If there is an error
     * @see <a href="http://www.textamerica.com/apicalls.aspx?call=Template.UpdateSection">http://www.textamerica.com/apicalls.aspx?call=Template.UpdateSection</a>
     */
    public String templateUpdateSection(int moblogID, String sectionID, String htmlCode) throws Exception {
        return templateUpdateSection(new Integer(moblogID), sectionID, htmlCode);
    }

    /**
     * Sets a template for a specified moblog from the textamerica template library
     * <p/>
     * Additional Parameters
     * <p/>
     * MoblogID (Number) - ID associated with the image that keywords are being added to<br/>
     * GraphicID (Number) - ID associated with the graphic set to use<br/>
     * LayoutID (Number) - ID associated with the layout to use<br/>
     *
     * @param moblogID  ID associated with the image that keywords are being added to
     * @param graphicID ID associated with the graphic set to use
     * @param layoutID  ID associated with the layout to use
     * @return
     * @throws Exception If there is an error
     * @see <a href="http://www.textamerica.com/apicalls.aspx?call=Template.SetTemplate">http://www.textamerica.com/apicalls.aspx?call=Template.SetTemplate</a>
     */
    public String setTemplate(Integer moblogID, Integer graphicID, Integer layoutID) throws Exception {
        Vector parameters = prepareDefaultRequestParameters();

        parameters.add(moblogID);
        parameters.add(graphicID);
        parameters.add(layoutID);

        Object response = xmlRpcClient.execute(TA_TEMPLATE_SETTEMPLATE, parameters);

        return (String) response;
    }

    /**
     * Sets a template for a specified moblog from the textamerica template library
     * <p/>
     * Additional Parameters
     * <p/>
     * MoblogID (Number) - ID associated with the image that keywords are being added to<br/>
     * GraphicID (Number) - ID associated with the graphic set to use<br/>
     * LayoutID (Number) - ID associated with the layout to use<br/>
     *
     * @param moblogID  ID associated with the image that keywords are being added to
     * @param graphicID ID associated with the graphic set to use
     * @param layoutID  ID associated with the layout to use
     * @return
     * @throws Exception If there is an error
     * @see <a href="http://www.textamerica.com/apicalls.aspx?call=Template.SetTemplate">http://www.textamerica.com/apicalls.aspx?call=Template.SetTemplate</a>
     */
    public String setTemplate(int moblogID, int graphicID, int layoutID) throws Exception {
        return setTemplate(new Integer(moblogID), new Integer(graphicID), new Integer(layoutID));
    }
}
package dev.chrismharris.main;

import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchAlbumsRequest;

import java.io.IOException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class ClientCredentialsExample {
    private static final String clientId = Keys.CLIENT_ID;
    private static final String clientSecret = Keys.CLIENT_SECRET;

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .build();
    private static final ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
            .build();

    public static void clientCredentials_Sync() {
        try {
            final ClientCredentials clientCredentials = clientCredentialsRequest.execute();

            // Set access token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(clientCredentials.getAccessToken());

            System.out.println("Expires in: " + clientCredentials.getExpiresIn());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void clientCredentials_Async() {
        try {
            final CompletableFuture<ClientCredentials> clientCredentialsFuture = clientCredentialsRequest.executeAsync();

            // Thread free to do other tasks...

            // Example Only. Never block in production code.
            final ClientCredentials clientCredentials = clientCredentialsFuture.join();

            // Set access token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(clientCredentials.getAccessToken());

            System.out.println("Expires in: " + clientCredentials.getExpiresIn());
        } catch (CompletionException e) {
            System.out.println("Error: " + e.getCause().getMessage());
        } catch (CancellationException e) {
            System.out.println("Async operation cancelled.");
        }
    }

    public static void main(String[] args) {
        clientCredentials_Sync();
        clientCredentials_Async();

        final SearchAlbumsRequest searchAlbumsRequest = spotifyApi.searchAlbums("Post Malone")
                .build();

        try {
            final Paging<AlbumSimplified> albumSimplifiedPaging = searchAlbumsRequest.execute();

            System.out.println("Total: " + albumSimplifiedPaging.getTotal());
            AlbumSimplified[] albums = albumSimplifiedPaging.getItems();
            for (AlbumSimplified a : albums) {
                System.out.println(a.getName());
                System.out.println("\t" + a.getReleaseDate());
                System.out.println("\t" + a.getId() + "\n\tBy: ");
                for (ArtistSimplified artist : a.getArtists()) {
                    System.out.println("\t\t" + artist.getName());
                    System.out.println("\t\t\t" + artist.getId());
                    System.out.println("\t\t\t" + artist.getHref());
                    System.out.println("\t\t\t" + artist.getUri());
                }
            }
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error when searching for album: " + e.getMessage());
        }
    }
}
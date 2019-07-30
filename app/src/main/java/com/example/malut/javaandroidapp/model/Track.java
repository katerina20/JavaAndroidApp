package com.example.malut.javaandroidapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.Locale;

public class Track implements Parcelable {

    private String trackName;
    private String artistName;
    private String country;
    private Date releaseDate;
    private int trackId;

    @SerializedName("collectionName")
    private String albumName;

    @SerializedName("trackTimeMillis")
    private long trackTime;

    @SerializedName("primaryGenreName")
    private String genreName;

    @SerializedName("artworkUrl100")
    private String trackImage;

    public Track(String trackName, String artistName, String country, Date releaseDate, int trackId, String albumName, long trackTime, String genreName, String trackImage) {
        this.trackName = trackName;
        this.artistName = artistName;
        this.country = country;
        this.releaseDate = releaseDate;
        this.trackId = trackId;
        this.albumName = albumName;
        this.trackTime = trackTime;
        this.genreName = genreName;
        this.trackImage = trackImage;
    }

    protected Track(Parcel in) {
        trackName = (String) in.readSerializable();
        artistName = (String) in.readSerializable();
        country = (String) in.readSerializable();
        albumName = (String) in.readSerializable();
        trackTime = (long) in.readSerializable();
        genreName = (String) in.readSerializable();
        trackImage = (String) in.readSerializable();
//        releaseDate = in.readSerializable() != null ? new Date((String) in.readSerializable()) : null;
        releaseDate = (java.util.Date) in.readSerializable();
        trackId = (int) in.readSerializable();
    }


    public static final Creator<Track> CREATOR = new Creator<Track>() {
        @Override
        public Track createFromParcel(Parcel in) {
            return new Track(in);
        }

        @Override
        public Track[] newArray(int size) {
            return new Track[size];
        }
    };

    public String getTrackName() {
        return trackName;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getCountry() {
        return country;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public String getAlbumName() {
        return albumName;
    }

    public long getTrackTimeInMillis() {
        return trackTime;
    }

    public int getTrackId() {
        return trackId;
    }

    public String getTrackTimeFormatted() {
        long second = (trackTime / 1000) % 60;
        long minute = (trackTime / (1000 * 60)) % 60;

        return String.format(Locale.US, "%02d:%02d", minute, second);
    }

    public String getGenreName() {
        return genreName;
    }

    public String getTrackImage() {
        return trackImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(trackName);
        dest.writeSerializable(artistName);
        dest.writeSerializable(country);
        dest.writeSerializable(albumName);
        dest.writeSerializable(trackTime);
        dest.writeSerializable(genreName);
        dest.writeSerializable(trackImage);
//        dest.writeSerializable(releaseDate != null ? String.valueOf(releaseDate.getTime()) : releaseDate);
        dest.writeSerializable(releaseDate);
        dest.writeSerializable(trackId);
    }
}

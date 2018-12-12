package pl.piotrgluszek.announcements.authentication;

public interface TokenManager {
    String generateToken(long userId);
    <T> T getSubject(String token);
    boolean validateToken(String token);
}

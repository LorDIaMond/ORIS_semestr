package interfaces.serv;

import modules.Match;
import modules.News;
import modules.User;

public interface IHomeService {
    public User getUserById(Long userId);
    public Match getCurrentMatch();
    public News getLatestNews();
}

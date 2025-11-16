package services;


import interfaces.repo.IMatchRepository;
import interfaces.repo.INewsRepository;
import interfaces.repo.IUserRepository;
import interfaces.serv.IHomeService;
import modules.Match;
import modules.News;
import modules.User;

public class HomeService implements IHomeService {

    private final IUserRepository userRepository;
    private final IMatchRepository matchRepository;
    private final INewsRepository newsRepository;

    public HomeService(IUserRepository userRepository, IMatchRepository matchRepository, INewsRepository newsRepository) {
        this.userRepository = userRepository;
        this.matchRepository = matchRepository;
        this.newsRepository = newsRepository;
    }

    @Override
    public User getUserById(Long userId) {
        return userId != null ? userRepository.findById(userId) : null;
    }

    @Override
    public Match getCurrentMatch() {
        return matchRepository.findCurrentMatch();
    }

    @Override
    public News getLatestNews() {
        return newsRepository.findLatestVisibleNews();
    }
}

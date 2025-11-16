package interfaces.repo;

import modules.News;

public interface INewsRepository {
    News findLatestVisibleNews();
}

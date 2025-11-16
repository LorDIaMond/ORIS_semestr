package interfaces.serv;

public interface IOrdersService {
    boolean toggleCart(Long userId, Long productId);
    boolean isInCart(Long userId, Long productId);
}

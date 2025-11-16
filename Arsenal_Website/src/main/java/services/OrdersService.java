package services;

import interfaces.repo.IOrdersRepository;
import interfaces.serv.IOrdersService;

public class OrdersService implements IOrdersService {
    private final IOrdersRepository ordersRepository;

    public OrdersService(IOrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    @Override
    public boolean toggleCart(Long userId, Long productId) {
        boolean isInCart = ordersRepository.isInOrders(userId, productId);
        if (isInCart) {
            ordersRepository.removeFromOrders(userId, productId);
        } else {
            ordersRepository.addToOrders(userId, productId, 1);
        }
        return !isInCart;
    }

    @Override
    public boolean isInCart(Long userId, Long productId) {
        return ordersRepository.isInOrders(userId, productId);
    }
}

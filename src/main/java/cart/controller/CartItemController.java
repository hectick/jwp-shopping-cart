package cart.controller;

import cart.auth.AuthenticatedMember;
import cart.auth.AuthenticationPrincipal;
import cart.dto.request.CartItemCreationRequest;
import cart.dto.response.CartItemResponse;
import cart.service.CartItemManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cart/cart-items")
public class CartItemController {

    private final CartItemManagementService cartItemManagementService;

    public CartItemController(final CartItemManagementService cartItemManagementService) {
        this.cartItemManagementService = cartItemManagementService;
    }

    @PostMapping
    public ResponseEntity<Void> postCartItems(@AuthenticationPrincipal AuthenticatedMember authenticatedMember,
                                              @Valid @RequestBody CartItemCreationRequest cartItemCreationRequest) {
        final Long productId = cartItemCreationRequest.getProductId();
        final long id = cartItemManagementService.save(authenticatedMember.getEmail(), productId);
        return ResponseEntity.created(URI.create("/cart/cart-items/" + id)).build();
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponse>> getCartItems(@AuthenticationPrincipal AuthenticatedMember authenticatedMember) {
        List<CartItemResponse> response = CartItemResponse.from(cartItemManagementService.findAll(authenticatedMember.getEmail()));
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Void> deleteCartItems(@AuthenticationPrincipal AuthenticatedMember authenticatedMember,
                                                @PathVariable Long cartItemId) {
        cartItemManagementService.delete(authenticatedMember.getEmail(), cartItemId);
        return ResponseEntity.noContent().build();
    }
}

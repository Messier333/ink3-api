package shop.ink3.api.order.shippingPolicy.service;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import shop.ink3.api.common.dto.PageResponse;
import shop.ink3.api.order.shippingPolicy.dto.ShippingPolicyCreateRequest;
import shop.ink3.api.order.shippingPolicy.dto.ShippingPolicyResponse;
import shop.ink3.api.order.shippingPolicy.dto.ShippingPolicyUpdateRequest;
import shop.ink3.api.order.shippingPolicy.entity.ShippingPolicy;
import shop.ink3.api.order.shippingPolicy.exception.ShippingPolicyNotFoundException;
import shop.ink3.api.order.shippingPolicy.repository.ShippingPolicyRepository;

@ExtendWith(MockitoExtension.class)
class ShippingPolicyServiceTest {
    @Mock
    ShippingPolicyRepository shippingPolicyRepository;

    @InjectMocks
    ShippingPolicyService shippingPolicyService;


    @Test
    @DisplayName("배송 정책 생성 - 성공")
    void createShippingPolicy() {
        // given
        ShippingPolicyCreateRequest request = new ShippingPolicyCreateRequest("테스트", 30000, 3000);
        when(shippingPolicyRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        // when
        ShippingPolicyResponse response = shippingPolicyService.createShippingPolicy(request);

        // then
        assertNotNull(response);
        assertEquals("테스트", response.getName());
        assertEquals(3000, response.getFee());
    }

    @Test
    @DisplayName("배송 정책 조회 - 성공")
    void getShippingPolicy_성공() {
        // given
        ShippingPolicy policy = ShippingPolicy.builder()
                .id(1L)
                .name("테스트")
                .threshold(30000)
                .fee(1000)
                .isAvailable(false)
                .createdAt(LocalDateTime.now())
                .build();
        when(shippingPolicyRepository.findById(1L)).thenReturn(Optional.of(policy));

        // when
        ShippingPolicyResponse response = shippingPolicyService.getShippingPolicy(1L);

        // then
        assertNotNull(response);
        assertEquals("테스트", response.getName());
    }

    @Test
    @DisplayName("배송 정책 조회 - 실패")
    void getShippingPolicy_실패 () {
        // given
        when(shippingPolicyRepository.findById(1L)).thenReturn(Optional.empty());

        // when, then
        assertThrows(ShippingPolicyNotFoundException.class,
                () -> shippingPolicyService.getShippingPolicy(1L));
    }

    @Test
    @DisplayName("배송 정책 수정 - 성공")
    void updateShippingPolicy_성공() {
        // given
        ShippingPolicy policy = ShippingPolicy.builder()
                .id(1L)
                .name("변경전")
                .threshold(30000)
                .fee(1000)
                .isAvailable(false)
                .createdAt(LocalDateTime.now())
                .build();
        when(shippingPolicyRepository.findById(1L)).thenReturn(Optional.of(policy));
        ShippingPolicyUpdateRequest updateRequest = new ShippingPolicyUpdateRequest("변경후", 9000, 5500);
        when(shippingPolicyRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        ShippingPolicyResponse response = shippingPolicyService.updateShippingPolicy(1L, updateRequest);

        // then
        assertEquals("변경후", response.getName());
        assertEquals(9000, response.getThreshold());
        assertEquals(5500, response.getFee());
    }

    @Test
    @DisplayName("배송 정책 수정 - 실패")
    void updateShippingPolicy_실패() {
        // given
        ShippingPolicyUpdateRequest updateRequest = new ShippingPolicyUpdateRequest("변경후", 9000, 5500);
        when(shippingPolicyRepository.findById(1L)).thenReturn(Optional.empty());

        // when, then
        assertThrows(ShippingPolicyNotFoundException.class,
                () -> shippingPolicyService.updateShippingPolicy(1L, updateRequest));
    }

    @Test
    @DisplayName("배송 정책 삭제 - 성공")
    void deleteShippingPolicy_성공() {
        // given
        ShippingPolicy policy = ShippingPolicy.builder()
                .id(1L)
                .name("변경전")
                .threshold(30000)
                .fee(1000)
                .isAvailable(false)
                .createdAt(LocalDateTime.now())
                .build();
        when(shippingPolicyRepository.findById(1L)).thenReturn(Optional.of(policy));

        // when
        shippingPolicyService.deleteShippingPolicy(1L);

        // then
        verify(shippingPolicyRepository).deleteById(1L);
    }

    @Test
    @DisplayName("배송 정책 삭제 - 실패")
    void deleteShippingPolicy_실패() {
        // given
        when(shippingPolicyRepository.findById(1L)).thenReturn(Optional.empty());

        // when, then
        assertThrows(ShippingPolicyNotFoundException.class,
                () -> shippingPolicyService.deleteShippingPolicy(1L));
    }

    @Test
    @DisplayName("배송 정책 전체 리스트 조회 - 성공")
    void getShippingPolicyList_성공(){
        // given
        ShippingPolicy policy1 = ShippingPolicy.builder().id(1L).name("테스트1").threshold(30000).fee(1000).build();
        ShippingPolicy policy2 = ShippingPolicy.builder().id(2L).name("테스트2").threshold(25000).fee(2000).build();
        List<ShippingPolicy> list = List.of(policy1, policy2);
        Pageable pageable = PageRequest.of(0, 10);
        Page<ShippingPolicy> page = new PageImpl<>(list, pageable, list.size());

        when(shippingPolicyRepository.findAll(pageable)).thenReturn(page);

        // when
        PageResponse<ShippingPolicyResponse> response = shippingPolicyService.getShippingPolicyList(pageable);

        // then
        assertEquals(2, response.content().size());
        assertEquals("테스트1", response.content().get(0).getName());
        assertEquals("테스트2", response.content().get(1).getName());
    }


    @Test
    @DisplayName("현재 배송 정책 확인 - 성공")
    void checkShippingPolicy_성공() {
        // given
        ShippingPolicy policy = ShippingPolicy.builder()
                .id(1L)
                .name("테스트1")
                .threshold(30000)
                .fee(1000)
                .isAvailable(false)
                .createdAt(LocalDateTime.now())
                .build();
        when(shippingPolicyRepository.findByIsAvailableTrue()).thenReturn(Optional.of(policy));

        // when
        ShippingPolicyResponse response = shippingPolicyService.getActivateShippingPolicy();

        // then
        assertEquals(response.getName(),"테스트1");
    }

    @Test
    @DisplayName("배송 정책 비활성화 - 성공")
    void deactivate_성공() {
        // given
        ShippingPolicy policy = ShippingPolicy.builder()
                .id(1L)
                .name("테스트")
                .threshold(30000)
                .fee(1000)
                .isAvailable(false)
                .createdAt(LocalDateTime.now())
                .build();
        when(shippingPolicyRepository.findById(any())).thenReturn(Optional.of(policy));
        when(shippingPolicyRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        shippingPolicyService.deactivate(1L);

        // then
        assertFalse(policy.getIsAvailable());
    }

    @Test
    @DisplayName("배송 정책 비활성화 - 실패")
    void deactivate_실패() {
        // given
        when(shippingPolicyRepository.findById(1L)).thenReturn(Optional.empty());

        // when, then
        assertThrows(ShippingPolicyNotFoundException.class,
                () -> shippingPolicyService.deactivate(1L));
    }


    @Test
    @DisplayName("배송 정책 활성화 - 성공")
    void activate_성공() {
        // given
        ShippingPolicy policy = ShippingPolicy.builder()
                .id(1L)
                .name("테스트")
                .threshold(30000)
                .fee(1000)
                .isAvailable(false)
                .createdAt(LocalDateTime.now())
                .build();
        when(shippingPolicyRepository.findById(any())).thenReturn(Optional.of(policy));
        when(shippingPolicyRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        shippingPolicyService.activate(1L);

        // then
        assertTrue(policy.getIsAvailable());
    }

    @Test
    @DisplayName("배송 정책 활성화 - 실패")
    void activate_실패() {
        // given
        when(shippingPolicyRepository.findById(1L)).thenReturn(Optional.empty());

        // when, then
        assertThrows(ShippingPolicyNotFoundException.class,
                () -> shippingPolicyService.activate(1L));
    }
}
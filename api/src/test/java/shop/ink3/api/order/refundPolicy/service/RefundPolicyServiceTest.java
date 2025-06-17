package shop.ink3.api.order.refundPolicy.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import shop.ink3.api.common.dto.PageResponse;
import shop.ink3.api.order.refundPolicy.dto.RefundPolicyCreateRequest;
import shop.ink3.api.order.refundPolicy.dto.RefundPolicyResponse;
import shop.ink3.api.order.refundPolicy.dto.RefundPolicyUpdateRequest;
import shop.ink3.api.order.refundPolicy.entity.RefundPolicy;
import shop.ink3.api.order.refundPolicy.exception.RefundPolicyNotFoundException;
import shop.ink3.api.order.refundPolicy.repository.RefundPolicyRepository;

@ExtendWith(MockitoExtension.class)
class RefundPolicyServiceTest {

    @Mock
    RefundPolicyRepository refundPolicyRepository;

    @InjectMocks
    RefundPolicyService refundPolicyService;

    @Test
    @DisplayName("반품 정책 생성 - 성공")
    void createRefundPolicy_성공() {
        // given
        RefundPolicyCreateRequest request = new RefundPolicyCreateRequest("테스트", 7, 30, 3000);
        when(refundPolicyRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        // when
        RefundPolicyResponse response = refundPolicyService.createRefundPolicy(request);

        // then
        assertNotNull(response);
        assertEquals("테스트", response.getName());
    }

    @Test
    @DisplayName("반품 정책 조회 - 성공")
    void getRefundPolicy_성공() {
        // given
        RefundPolicy policy = RefundPolicy.builder().id(1L).name("테스트").build();
        when(refundPolicyRepository.findById(1L)).thenReturn(Optional.of(policy));

        // when
        RefundPolicyResponse response = refundPolicyService.getRefundPolicy(1L);

        // then
        assertNotNull(response);
        assertEquals("테스트", response.getName());
    }

    @Test
    @DisplayName("반품 정책 조회 - 실패")
    void getRefundPolicy_실패() {
        // given
        when(refundPolicyRepository.findById(1L)).thenReturn(Optional.empty());

        // when, then
        assertThrows(RefundPolicyNotFoundException.class,
                () -> refundPolicyService.getRefundPolicy(1L));
    }

    @Test
    @DisplayName("반품 정책 목록 조회 - 성공")
    void getRefundPolicyList_성공() {
        // given
        List<RefundPolicy> policies = List.of(
                RefundPolicy.builder().id(1L).name("테스트1").build(),
                RefundPolicy.builder().id(2L).name("테스트2").build()
        );
        Pageable pageable = PageRequest.of(0, 10);
        Page<RefundPolicy> page = new PageImpl<>(policies, pageable, policies.size());
        when(refundPolicyRepository.findAll(pageable)).thenReturn(page);

        // when
        PageResponse<RefundPolicyResponse> response = refundPolicyService.getRefundPolicyList(pageable);

        // then
        assertEquals(2, response.content().size());
        assertEquals("테스트1", response.content().get(0).getName());
        assertEquals("테스트2", response.content().get(1).getName());
    }

    @Test
    @DisplayName("활성화된 반품 정책 목록 조회 - 성공")
    void getAvailableRefundPolicyList_성공() {
        // given
        RefundPolicy policy = RefundPolicy.builder().id(1L).name("테스트").isAvailable(true).build();
        when(refundPolicyRepository.findByIsAvailableTrue()).thenReturn(policy);

        // when
        RefundPolicyResponse response = refundPolicyService.getAvailableRefundPolicy();

        // then
        assertEquals("테스트",response.getName());
        assertTrue(response.getIsAvailable());
    }

    @Test
    @DisplayName("반품 정책 수정 - 성공")
    void updateRefundPolicy_성공() {
        // given
        RefundPolicy policy = RefundPolicy.builder().id(1L).name("변경점").build();
        RefundPolicyUpdateRequest request = new RefundPolicyUpdateRequest("변경후", 10, 15, 3000);
        when(refundPolicyRepository.findById(1L)).thenReturn(Optional.of(policy));

        // when
        RefundPolicyResponse response = refundPolicyService.updateRefundPolicy(1L, request);

        // then
        assertEquals("변경후", response.getName());
    }

    @Test
    @DisplayName("반품 정책 수정 - 실패")
    void updateRefundPolicy_실패() {
        // given
        RefundPolicyUpdateRequest request = new RefundPolicyUpdateRequest("변경후", 10, 15, 3000);
        when(refundPolicyRepository.findById(1L)).thenReturn(Optional.empty());

        // when, then
        assertThrows(RefundPolicyNotFoundException.class,
                () -> refundPolicyService.updateRefundPolicy(1L, request));
    }

    @Test
    @DisplayName("반품 정책 삭제 - 성공")
    void deleteRefundPolicy_성공() {
        // given
        RefundPolicy policy = RefundPolicy.builder().id(1L).name("테스트").build();
        when(refundPolicyRepository.findById(1L)).thenReturn(Optional.of(policy));

        // when
        refundPolicyService.deleteRefundPolicy(1L);

        // then
        verify(refundPolicyRepository).deleteById(1L);
    }

    @Test
    @DisplayName("반품 정책 삭제 - 실패")
    void deleteRefundPolicy_실패() {
        // given
        when(refundPolicyRepository.findById(1L)).thenReturn(Optional.empty());

        // when, then
        assertThrows(RefundPolicyNotFoundException.class,
                () -> refundPolicyService.deleteRefundPolicy(1L));
    }

    @Test
    @DisplayName("반품 정책 비활성화 - 성공")
    void deactivate_성공() {
        // given
        RefundPolicy policy = RefundPolicy.builder().id(1L).isAvailable(true).build();
        when(refundPolicyRepository.findById(1L)).thenReturn(Optional.of(policy));
        when(refundPolicyRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        // when
        refundPolicyService.deactivate(1L);

        // then
        assertFalse(policy.getIsAvailable());
    }

    @Test
    @DisplayName("반품 정책 비활성화 - 실패")
    void deactivate_실패() {
        // given
        when(refundPolicyRepository.findById(1L)).thenReturn(Optional.empty());

        // when, then
        assertThrows(RefundPolicyNotFoundException.class,
                () -> refundPolicyService.deactivate(1L));
    }

    @Test
    @DisplayName("반품 정책 활성화 - 성공")
    void activate_성공() {
        // given
        RefundPolicy policy = RefundPolicy.builder().id(1L).isAvailable(false).build();
        when(refundPolicyRepository.findById(1L)).thenReturn(Optional.of(policy));
        when(refundPolicyRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        // when
        refundPolicyService.activate(1L);

        // then
        assertTrue(policy.getIsAvailable());
    }

    @Test
    @DisplayName("반품 정책 활성화 - 실패")
    void activate_실패() {
        // given
        when(refundPolicyRepository.findById(1L)).thenReturn(Optional.empty());

        // when, then
        assertThrows(RefundPolicyNotFoundException.class,
                () -> refundPolicyService.activate(1L));
    }
}

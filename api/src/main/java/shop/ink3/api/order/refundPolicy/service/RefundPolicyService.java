package shop.ink3.api.order.refundPolicy.service;

import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.ink3.api.common.dto.PageResponse;
import shop.ink3.api.order.refundPolicy.dto.RefundPolicyCreateRequest;
import shop.ink3.api.order.refundPolicy.dto.RefundPolicyResponse;
import shop.ink3.api.order.refundPolicy.dto.RefundPolicyUpdateRequest;
import shop.ink3.api.order.refundPolicy.entity.RefundPolicy;
import shop.ink3.api.order.refundPolicy.exception.RefundPolicyNotFoundException;
import shop.ink3.api.order.refundPolicy.repository.RefundPolicyRepository;

@Transactional
@RequiredArgsConstructor
@Service
public class RefundPolicyService {

    private final RefundPolicyRepository refundPolicyRepository;

    // 생성
    public RefundPolicyResponse createRefundPolicy(RefundPolicyCreateRequest request) {
        RefundPolicy refundPolicy = RefundPolicy.builder()
                .name(request.getName())
                .returnDeadLine(request.getReturnDeadLine())
                .defectReturnDeadLine(request.getDefectReturnDeadLine())
                .isAvailable(true)
                .createdAt(LocalDateTime.now())
                .refundShippingFee(request.getRefundShippingFee())
                .build();

        RefundPolicyResponse availableRefundPolicy = getAvailableRefundPolicy();
        if(availableRefundPolicy != null) {
            deactivate(availableRefundPolicy.getId());
        }
        return RefundPolicyResponse.from(refundPolicyRepository.save(refundPolicy));
    }

    // 조회
    @Transactional(readOnly = true)
    public RefundPolicyResponse getRefundPolicy(long refundPolicyId) {
        RefundPolicy refundPolicy = getRefundPolicyOrThrow(refundPolicyId);
        return RefundPolicyResponse.from(refundPolicy);
    }

    // 전체 정책 list 조회
    @Transactional(readOnly = true)
    public PageResponse<RefundPolicyResponse> getRefundPolicyList(Pageable pageable) {
        Page<RefundPolicy> page = refundPolicyRepository.findAll(pageable);
        Page<RefundPolicyResponse> responsePage = page.map(RefundPolicyResponse::from);
        return PageResponse.from(responsePage);
    }

    // 활성화된 정책 조회
    @Transactional(readOnly = true)
    public RefundPolicyResponse getAvailableRefundPolicy() {
        RefundPolicy refundPolicy = refundPolicyRepository.findByIsAvailableTrue();
        if(refundPolicy == null) {
            return null;
        }
        return RefundPolicyResponse.from(refundPolicy);
    }

    // 수정
    public RefundPolicyResponse updateRefundPolicy(long refundPolicyId, RefundPolicyUpdateRequest request) {
        RefundPolicy refundPolicy = getRefundPolicyOrThrow(refundPolicyId);
        refundPolicy.update(request);
        return RefundPolicyResponse.from(refundPolicy);
    }

    // 삭제
    public void deleteRefundPolicy(long refundPolicyId) {
        getRefundPolicyOrThrow(refundPolicyId);
        refundPolicyRepository.deleteById(refundPolicyId);
    }

    // 활성화
    public void activate(long refundPolicyId) {
        RefundPolicyResponse availableRefundPolicy = getAvailableRefundPolicy();
        if(availableRefundPolicy != null) {
            deactivate(availableRefundPolicy.getId());
        }
        RefundPolicy refundPolicy = getRefundPolicyOrThrow(refundPolicyId);
        refundPolicy.activate();
        refundPolicyRepository.save(refundPolicy);
    }

    // 비활성화
    public void deactivate(long refundPolicyId) {
        RefundPolicy refundPolicy = getRefundPolicyOrThrow(refundPolicyId);
        refundPolicy.deactivate();
        refundPolicyRepository.save(refundPolicy);
    }


    // 조회 로직
    protected RefundPolicy getRefundPolicyOrThrow(long refundPolicyId) {
        Optional<RefundPolicy> optionalRefundPolicy = refundPolicyRepository.findById(refundPolicyId);
        if (!optionalRefundPolicy.isPresent()) {
            throw new RefundPolicyNotFoundException(refundPolicyId);
        }
        return optionalRefundPolicy.get();
    }
}

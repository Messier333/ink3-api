package shop.ink3.api.order.packaging.service;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import shop.ink3.api.common.dto.PageResponse;
import shop.ink3.api.order.packaging.dto.PackagingCreateRequest;
import shop.ink3.api.order.packaging.dto.PackagingResponse;
import shop.ink3.api.order.packaging.dto.PackagingUpdateRequest;
import shop.ink3.api.order.packaging.entity.Packaging;
import shop.ink3.api.order.packaging.exception.PackagingNotFoundException;
import shop.ink3.api.order.packaging.repository.PackagingRepository;

@ExtendWith(MockitoExtension.class)
class PackagingServiceTest {

    @Mock
    PackagingRepository packagingRepository;

    @InjectMocks
    PackagingService packagingService;

    @Test
    @DisplayName("포장 정책 생성 - 성공")
    void createPackaging_성공() {
        // given
        PackagingCreateRequest request = new PackagingCreateRequest("테스트", 1000);
        when(packagingRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        // when
        PackagingResponse response = packagingService.createPackaging(request);

        // then
        assertNotNull(response);
        assertEquals("테스트", response.getName());
        assertEquals(1000, response.getPrice());
    }

    @Test
    @DisplayName("포장 정책 조회 - 성공")
    void getPackaging_성공() {
        // given
        Packaging packaging = Packaging.builder().id(1L).name("테스트").price(1000).build();
        when(packagingRepository.findById(1L)).thenReturn(Optional.of(packaging));

        // when
        PackagingResponse response = packagingService.getPackaging(1L);

        // then
        assertNotNull(response);
        assertEquals("테스트", response.getName());
    }

    @Test
    @DisplayName("포장 정책 조회 - 실패")
    void getPackaging_실패() {
        // given
        when(packagingRepository.findById(1L)).thenReturn(Optional.empty());

        // when, then
        assertThrows(PackagingNotFoundException.class,
                () -> packagingService.getPackaging(1L));
    }

    @Test
    @DisplayName("포장 정책 목록 조회 - 성공")
    void getPackagingList_성공() {
        // given
        List<Packaging> list = List.of(
                Packaging.builder().id(1L).name("테스트1").build(),
                Packaging.builder().id(2L).name("테스트2").build()
        );
        Pageable pageable = PageRequest.of(0, 10);
        Page<Packaging> page = new PageImpl<>(list, pageable, list.size());
        when(packagingRepository.findAll(pageable)).thenReturn(page);

        // when
        PageResponse<PackagingResponse> response = packagingService.getPackagingList(pageable);

        // then
        assertEquals(2, response.content().size());
        assertEquals("테스트1", response.content().get(0).getName());
        assertEquals("테스트2", response.content().get(1).getName());
    }

    @Test
    @DisplayName("활성화된 포장 정책 목록 조회 - 성공")
    void getAvailablePackagingList_성공() {
        // given
        List<Packaging> list = List.of(Packaging.builder().id(1L).name("활성 포장").isAvailable(true).build());
        Pageable pageable = PageRequest.of(0, 10);
        Page<Packaging> page = new PageImpl<>(list, pageable, list.size());
        when(packagingRepository.findAllByIsAvailableTrue(pageable)).thenReturn(page);

        // when
        PageResponse<PackagingResponse> response = packagingService.getAvailablePackagingList(pageable);

        // then
        assertEquals(1, response.content().size());
        assertTrue(response.content().get(0).getIsAvailable());
    }

    @Test
    @DisplayName("포장 정책 수정 - 성공")
    void updatePackaging_성공() {
        // given
        Packaging packaging = Packaging.builder().id(1L).name("이전 포장").build();
        PackagingUpdateRequest request = new PackagingUpdateRequest("수정된 포장", 1500);
        when(packagingRepository.findById(1L)).thenReturn(Optional.of(packaging));
        when(packagingRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        // when
        PackagingResponse response = packagingService.updatePackaging(1L, request);

        // then
        assertEquals("수정된 포장", response.getName());
        assertEquals(1500, response.getPrice());
    }

    @Test
    @DisplayName("포장 정책 수정 - 실패")
    void updatePackaging_실패() {
        // given
        PackagingUpdateRequest request = new PackagingUpdateRequest("수정된 포장", 1500);
        when(packagingRepository.findById(1L)).thenReturn(Optional.empty());

        // when, then
        assertThrows(PackagingNotFoundException.class,
                () -> packagingService.updatePackaging(1L, request));
    }

    @Test
    @DisplayName("포장 정책 삭제 - 성공")
    void deletePackaging_성공() {
        // given
        Packaging packaging = Packaging.builder().id(1L).name("삭제 포장").build();
        when(packagingRepository.findById(1L)).thenReturn(Optional.of(packaging));

        // when
        packagingService.deletePackaging(1L);

        // then
        verify(packagingRepository).deleteById(1L);
    }

    @Test
    @DisplayName("포장 정책 삭제 - 실패")
    void deletePackaging_실패() {
        // given
        when(packagingRepository.findById(1L)).thenReturn(Optional.empty());

        // when, then
        assertThrows(PackagingNotFoundException.class,
                () -> packagingService.deletePackaging(1L));
    }

    @Test
    @DisplayName("포장 정책 비활성화 - 성공")
    void deactivate_성공() {
        // given
        Packaging packaging = Packaging.builder().id(1L).isAvailable(true).build();
        when(packagingRepository.findById(1L)).thenReturn(Optional.of(packaging));
        when(packagingRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        // when
        packagingService.deactivate(1L);

        // then
        assertFalse(packaging.getIsAvailable());
    }

    @Test
    @DisplayName("포장 정책 비활성화 - 실패")
    void deactivate_실패() {
        // given
        when(packagingRepository.findById(1L)).thenReturn(Optional.empty());

        // when, then
        assertThrows(PackagingNotFoundException.class,
                () -> packagingService.deactivate(1L));
    }

    @Test
    @DisplayName("포장 정책 활성화 - 성공")
    void activate_성공() {
        // given
        Packaging packaging = Packaging.builder().id(1L).isAvailable(false).build();
        when(packagingRepository.findById(1L)).thenReturn(Optional.of(packaging));
        when(packagingRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        // when
        packagingService.activate(1L);

        // then
        assertTrue(packaging.getIsAvailable());
    }

    @Test
    @DisplayName("포장 정책 활성화 - 실패")
    void activate_실패() {
        // given
        when(packagingRepository.findById(1L)).thenReturn(Optional.empty());

        // when, then
        assertThrows(PackagingNotFoundException.class,
                () -> packagingService.activate(1L));
    }
}

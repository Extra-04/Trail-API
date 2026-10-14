package io.github.extra04.trail.api;

import org.jetbrains.annotations.NotNull;

/**
 * 서버에 등록된 잔상 하나의 읽기 전용 정보.
 *
 * <p>플러그인 내부 자료형을 그대로 내보내지 않으려고 만든 뷰다. 내부 구조가 바뀌어도
 * 이 인터페이스가 그대로이면 다른 플러그인은 깨지지 않는다.
 *
 * <p>관리자가 {@code /trail reload} 를 하면 정의가 교체된다. 이 객체를 오래 들고 있지 말고
 * 필요할 때마다 {@link TrailAPI#getTrail(String)} 으로 다시 조회할 것.
 */
public interface TrailInfo {

    /** 잔상 ID. 항상 소문자다. */
    @NotNull
    String getId();

    /**
     * 메뉴에 보이는 이름. 정의 파일의 {@code display-name} 에 적은 글을 그대로 돌려준다
     * (색 코드가 섞여 있을 수 있다). 적지 않았다면 ID 와 같다.
     */
    @NotNull
    String getDisplayName();

    /**
     * 이 잔상을 쓰는 데 필요한 권한. 아무나 쓸 수 있으면 빈 문자열이다.
     */
    @NotNull
    String getPermission();

    /** 잔상이 어떤 모양으로 남는지. */
    @NotNull
    TrailStyle getStyle();

    /**
     * 메뉴 아이콘으로 쓰는 재질 이름 (예: {@code BLAZE_POWDER}). 항상 유효한
     * {@link org.bukkit.Material} 이름이다.
     *
     * <p>잔상이 ItemsAdder·Nexo 아이템을 쓰는 경우에는 대체 재질이 들어간다. 이 값은 메뉴
     * 아이콘의 바탕일 뿐이므로, 실제로 어떤 그림이 남는지를 알아내는 데 쓰면 안 된다.
     */
    @NotNull
    String getMaterial();
}

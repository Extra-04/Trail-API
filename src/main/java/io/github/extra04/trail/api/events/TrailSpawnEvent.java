package io.github.extra04.trail.api.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * 잔상 하나가 남겨지기 직전에 발생한다. 취소하면 그 잔상은 나타나지 않는다.
 *
 * <h2>⚠ 이 이벤트는 매우 자주 발생한다</h2>
 * 움직이는 플레이어마다, 설정된 간격(잔상 정의의 {@code interval}) 마다 한 번씩 불린다.
 * 가만히 서 있을 때 남기는 잔상도 마찬가지다. 20명이 뛰어다니면 초당 수십 번이다.
 * <b>리스너 안에서 무거운 일을 하지 말 것</b> — 파일 입출력, 데이터베이스 조회, 동기 네트워크
 * 호출은 서버를 그대로 끌어내린다.
 *
 * <p>플러그인은 리스너가 하나도 없으면 이 이벤트 객체를 아예 만들지 않는다. 따라서
 * 이 이벤트를 쓰지 않는 서버에는 부담이 없다. 다만 리스너를 등록하는 순간 그 비용은
 * 등록한 쪽이 지는 것이다.
 *
 * <p>특정 상황에서만 막고 싶다면, 그 조건을 서버 설정으로 처리할 수 있는지 먼저 보라.
 * {@code config.yml} 의 {@code exceptions} 항목이 투명화·벤시·관전 모드·월드·게임모드·권한별
 * 예외를 이미 다룬다. 그걸로 되는 일이라면 이벤트보다 그쪽이 훨씬 싸다.
 *
 * <h2>취소했을 때</h2>
 * 그 자리에 잔상이 생기지 않을 뿐이다. 다음 잔상은 평소처럼 다음 간격이 지난 뒤에 이어서
 * 나오므로, 취소한 상태로 계속 움직여도 이벤트가 매 걸음 불리지는 않는다.
 */
public class TrailSpawnEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();

    private final String trailId;
    private final Location location;
    private boolean cancelled;

    public TrailSpawnEvent(@NotNull Player player, @NotNull String trailId, @NotNull Location location) {
        super(player);
        this.trailId = trailId;
        this.location = location;
    }

    /** 남겨지려는 잔상의 ID. */
    @NotNull
    public String getTrailId() {
        return trailId;
    }

    /**
     * 잔상을 남길 기준 위치, 곧 그 순간 플레이어가 있는 곳(탈것을 탔다면 앉아 있는 높이).
     *
     * <p>잔상 정의의 뒤쪽 거리·높이·흩뜨리기는 이 위치를 기준으로 그 뒤에 더해지므로, 실제로 놓이는
     * 좌표와는 조금 다를 수 있다. 돌려주는 객체는 복사본이라 고쳐도 실제 위치에 영향을 주지 않는다.
     */
    @NotNull
    public Location getLocation() {
        return location.clone();
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}

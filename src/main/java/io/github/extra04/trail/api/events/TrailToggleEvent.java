package io.github.extra04.trail.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * 플레이어의 잔상 표시가 켜지거나 꺼지기 직전에 발생한다. 취소하면 이전 상태가 유지된다.
 *
 * <p>예: 특정 월드나 미니게임 중에는 끄지 못하게 막고 싶을 때 쓴다.
 */
public class TrailToggleEvent extends PlayerEvent implements Cancellable {

    /** 무엇이 이 변경을 일으켰는지. */
    public enum Cause {
        /** 플레이어가 {@code /trail} 을 썼다. */
        COMMAND,
        /** 플레이어가 메뉴의 ON/OFF 버튼을 눌렀다. */
        GUI,
        /** 다른 플러그인이 API 로 바꿨다. */
        API,
    }

    private static final HandlerList HANDLERS = new HandlerList();

    private final boolean enabling;
    private final Cause cause;
    private boolean cancelled;

    public TrailToggleEvent(@NotNull Player player, boolean enabling, @NotNull Cause cause) {
        super(player);
        this.enabling = enabling;
        this.cause = cause;
    }

    /** 켜려는 중이면 {@code true}, 끄려는 중이면 {@code false}. */
    public boolean isEnabling() {
        return enabling;
    }

    /** 이 변경을 일으킨 주체. */
    @NotNull
    public Cause getCause() {
        return cause;
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

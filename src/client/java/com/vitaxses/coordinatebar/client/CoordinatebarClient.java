package com.vitaxses.coordinatebar.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.lwjgl.glfw.GLFW;

public class CoordinatebarClient implements ClientModInitializer {

    private static final KeyBinding KEY_BINDING = KeyBindingHelper.registerKeyBinding(
            new KeyBinding(
                    "key.coordinatebar.press_shoulddisplay",
                    InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_KP_9, "CoordinateBar"));
    private static final KeyBinding KEY_BINDING_ADVANCED = KeyBindingHelper.registerKeyBinding(
            new KeyBinding(
                    "key.coordinatebar.press_advanced",
                    InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, "CoordinateBar"));

    private static final KeyBinding KEY_BINDING_COLORS = KeyBindingHelper.registerKeyBinding(
            new KeyBinding(
                    "key.coordinatebar.press_color",
                    InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F10, "CoordinateBar"));


    private boolean shouldDisplay = false;
    private boolean advanced = false;
    private boolean colors = true;


    @Override
    public void onInitializeClient() {

        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            checkBooleanAndSendCoordinates(client.player);
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (KEY_BINDING.wasPressed()) {
                shouldDisplay = !shouldDisplay;
            }
        });
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (KEY_BINDING_ADVANCED.wasPressed()) {
                advanced = !advanced;
            }
        });
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (KEY_BINDING_COLORS.wasPressed()) {
                colors = !colors;
            }
        });

    }

    private void checkBooleanAndSendCoordinates(ClientPlayerEntity player) {
        if (player != null) {
            if (shouldDisplay) {
                if (!advanced) {
                    int x = (int) player.getX();
                    int y = (int) player.getY();
                    int z = (int) player.getZ();

                    if (colors) {
                        Text message = Text.literal("X ").formatted(Formatting.WHITE)
                                .append(Text.literal(String.valueOf(x)).formatted(Formatting.GREEN))
                                .append(Text.literal(" Y ").formatted(Formatting.WHITE))
                                .append(Text.literal(String.valueOf(y)).formatted(Formatting.GREEN))
                                .append(Text.literal(" Z ").formatted(Formatting.WHITE))
                                .append(Text.literal(String.valueOf(z)).formatted(Formatting.GREEN));

                        player.sendMessage(message, true);
                    } else {
                        Text message = Text.literal("X " + x + " Y " + y + " Z " + z);
                        player.sendMessage(message, true);
                    }
                } else {
                    // ADVANCED
                    double x = player.getX();
                    double y = player.getY();
                    double z = player.getZ();

                    if (colors) {
                        Text message = Text.literal("X ").formatted(Formatting.WHITE)
                                .append(Text.literal(String.valueOf(x)).formatted(Formatting.GREEN))
                                .append(Text.literal(" Y ").formatted(Formatting.WHITE))
                                .append(Text.literal(String.valueOf(y)).formatted(Formatting.GREEN))
                                .append(Text.literal(" Z ").formatted(Formatting.WHITE))
                                .append(Text.literal(String.valueOf(z)).formatted(Formatting.GREEN));

                        player.sendMessage(message, true);
                    } else {
                        Text message = Text.literal("X " + x + " Y " + y + " Z " + z);
                        player.sendMessage(message, true);
                    }
                }
            }
        }
    }

}


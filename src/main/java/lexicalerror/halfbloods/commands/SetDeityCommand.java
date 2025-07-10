package lexicalerror.halfbloods.commands;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import lexicalerror.halfbloods.ModComponents;
import lexicalerror.halfbloods.components.PlayerComponent;
import lexicalerror.halfbloods.deities.Deities;
import lexicalerror.halfbloods.deities.Deity;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.*;

public class SetDeityCommand {
    public static void register(){
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("setDeity")
                .then(argument("player", EntityArgumentType.entity())
                .then(argument("deity", StringArgumentType.string())
                        .executes(context -> executeSetDeity(EntityArgumentType.getPlayer(context, "player"), StringArgumentType.getString(context, "deity"), context))))));
    }

    private static int executeSetDeity(ServerPlayerEntity player, String deityString, CommandContext<ServerCommandSource> context) {
        PlayerComponent component = ModComponents.PLAYER_COMPONENT.get(player);

        Deity deity = Deities.fromString(deityString).getDeity();
        if (deity == null) {
            context.getSource().sendError(Text.literal("Invalid deity name: " + deityString));
            return 0;
        }
        component.setDeity(deity);

        context.getSource().sendFeedback(() -> Text.literal("%s's deity set to %s".formatted(player, deity)), false);
        return 0;
    }
}
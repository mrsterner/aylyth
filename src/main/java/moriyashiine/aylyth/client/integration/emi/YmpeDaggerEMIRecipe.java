package moriyashiine.aylyth.client.integration.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import moriyashiine.aylyth.common.recipe.YmpeDaggerDropRecipe;
import moriyashiine.aylyth.common.util.RenderUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class YmpeDaggerEMIRecipe implements EmiRecipe {
    private final Identifier id;
    public final EntityType<?> entityType;
    private final List<EmiIngredient>  input;
    private final List<EmiStack> output;

    public YmpeDaggerEMIRecipe(YmpeDaggerDropRecipe recipe) {
        this.id = recipe.getId();
        entityType = recipe.entity_type;
        this.output = List.of(EmiStack.of(recipe.getOutput()));
        this.input = List.of(EmiStack.of(new ItemStack(Items.AIR)));
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return ModEMIPlugin.YMPE_DAGGER_CATEGORY;
    }

    @Override
    public @Nullable Identifier getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return input;
    }

    @Override
    public List<EmiStack> getOutputs() {
        return output;
    }

    @Override
    public int getDisplayWidth() {
        return 76 + 18;
    }

    @Override
    public int getDisplayHeight() {
        return 18 + 18 + 2;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        Entity target = entityType.create(MinecraftClient.getInstance().world);

        widgets.addDrawable(18,18,20,20, (matrices, mouseX, mouseY, delta) -> {
            if (target instanceof LivingEntity livingEntity) {
                RenderUtils.drawEntity(18,18, 20, mouseX, mouseY, livingEntity, null);
            }
        });

        widgets.addTexture(EmiTexture.EMPTY_ARROW, 40, 10);

        widgets.addSlot(output.get(0), 58 + 9, 10).recipeContext(this);
    }
}
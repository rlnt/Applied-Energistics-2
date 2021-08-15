/*
 * This file is part of Applied Energistics 2.
 * Copyright (c) 2021, TeamAppliedEnergistics, All rights reserved.
 *
 * Applied Energistics 2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Applied Energistics 2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Applied Energistics 2.  If not, see <http://www.gnu.org/licenses/lgpl>.
 */

package appeng.recipes.entropy;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.StateHolder;

public interface StateMatcher {
    boolean matches(StateHolder<?, ?> state);

    void writeToPacket(FriendlyByteBuf buffer);

    static StateMatcher read(StateDefinition<?, ?> stateDefinition, FriendlyByteBuf buffer) {
        MatcherType type = buffer.readEnum(MatcherType.class);

        return switch (type) {
            case SINGLE -> SingleValueMatcher.readFromPacket(stateDefinition, buffer);
            case MULTIPLE -> MultipleValuesMatcher.readFromPacket(stateDefinition, buffer);
            case RANGE -> RangeValueMatcher.readFromPacket(stateDefinition, buffer);
        };

    }

    enum MatcherType {
        SINGLE, MULTIPLE, RANGE;
    }
}
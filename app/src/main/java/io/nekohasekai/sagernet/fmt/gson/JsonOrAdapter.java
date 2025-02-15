/******************************************************************************
 *                                                                            *
 * Copyright (C) 2021 by nekohasekai <sekai@neko.services>                    *
 * Copyright (C) 2021 by Max Lv <max.c.lv@gmail.com>                          *
 * Copyright (C) 2021 by Mygod Studio <contact-shadowsocks-android@mygod.be>  *
 *                                                                            *
 * This program is free software: you can redistribute it and/or modify       *
 * it under the terms of the GNU General Public License as published by       *
 * the Free Software Foundation, either version 3 of the License, or          *
 *  (at your option) any later version.                                       *
 *                                                                            *
 * This program is distributed in the hope that it will be useful,            *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of             *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the              *
 * GNU General Public License for more details.                               *
 *                                                                            *
 * You should have received a copy of the GNU General Public License          *
 * along with this program. If not, see <http://www.gnu.org/licenses/>.       *
 *                                                                            *
 ******************************************************************************/

package io.nekohasekai.sagernet.fmt.gson;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class JsonOrAdapter<X, Y> extends TypeAdapter<JsonOr<X, Y>> {

    private final Gson gson;
    private final TypeToken<X> typeX;
    private final TypeToken<Y> typeY;
    private final JsonToken tokenX;
    private final JsonToken tokenY;

    public JsonOrAdapter(Gson gson, TypeToken<X> typeX, TypeToken<Y> typeY, JsonToken tokenX, JsonToken tokenY) {
        this.gson = gson;
        this.typeX = typeX;
        this.typeY = typeY;
        this.tokenX = tokenX;
        this.tokenY = tokenY;
    }

    @Override
    public void write(JsonWriter out, JsonOr<X, Y> value) throws IOException {
        if (value.valueX != null) {
            gson.getAdapter(typeX).write(out, value.valueX);
        } else {
            gson.getAdapter(typeY).write(out, value.valueY);
        }
    }

    @Override
    public JsonOr<X, Y> read(JsonReader in) throws IOException {
        if (in.peek() == tokenX) {
            return new JsonOr<>(gson.getAdapter(typeX).read(in), null);
        } else {
            return new JsonOr<>(null, gson.getAdapter(typeY).read(in));
        }

    }
}

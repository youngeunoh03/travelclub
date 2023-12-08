package io.namoosori.tc.shared;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NameValue {
    //
    private String name;
    private String value;

    @Override
    public String toString() {
        //
        StringBuilder builder = new StringBuilder();

        builder.append("NameValue{")
                .append("name='").append(name).append('\'')
                .append(", value='").append(value).append('\'')
                .append('}');

        return builder.toString();
    }
}

package sommarengine.model;

public class TransferAttributes {
    public int size;
    public Type type;
    public TransferAttributes(Type type,int size){
        this.size = size;
        this.type = type;
    }

    public interface Type {
        int getIndex();
        TypeEnum getEnum();
    }

    private static class CustomType implements Type {

        private int index = 0;

        private CustomType(int index) {this.index = index;}

        @Override
        public int getIndex() {
            return index;
        }

        @Override
        public TypeEnum getEnum() {
            return TypeEnum.CUSTOM;
        }
    }

    public enum TypeEnum implements Type{
        POSITION(0),
        COLOR(3),
        UV(1),
        NORMALS(2),
        TEXTURE_ID(4),
        CUSTOM(-1);

        private int index = 0;

        private TypeEnum(int index) {this.index = index;}

        public int getIndex() {
            return index;
        }

        @Override
        public TypeEnum getEnum() {
            return this;
        }

        public static Type createCustom(int index) {
            return new CustomType(index);
        }
    }
}

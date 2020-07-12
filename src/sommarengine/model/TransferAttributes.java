package sommarengine.model;

public class TransferAttributes {
    public int size;
    private Type type;
    private int index = -1;
    public TransferAttributes(Type type,int size){
        this.size = size;
        this.type = type;
    }

    public TransferAttributes(int index,int size){
        this.size = size;
        this.index = index;
    }

    public int getIndex() {
        if(index >= 0) return index;
        return type.getIndex();
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
        COLOR(4),
        UV(3),
        NORMALS(3),
        TEXTURE_ID(2),
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

export interface Menus {
    id: string;
    pid?: string;
    icon?: string;
    index: string;
    title: string;
    class: string;
    // permiss?: string;
    children?: Menus[];
}
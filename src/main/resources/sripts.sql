
CREATE OR REPLACE FUNCTION update_last_modified_column()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.modify_date = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_last_modified
    BEFORE UPDATE ON plans
    FOR EACH ROW
EXECUTE FUNCTION update_last_modified_column();
